package com.example.thienpro.mvp_firebase.model.Impl;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ThienPro on 11/17/2017.
 */

public class PostInteractorImpl implements PostInteractor {
    private static final String POST = "posts";
    private static final String USER = "users";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<Post> postList;
    private Date today;
    private String day;
    private SimpleDateFormat simpleDateFormat;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference ref;

    public PostInteractorImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        postList = new ArrayList<>();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void writeNewPost(final String content, final Uri filePath, final PostCallback callback) {
        today = new Date();
        today.getDate();
        simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        day = simpleDateFormat.format(today);

        if (filePath != null) {
            ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //get url image
                            ref.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(final Uri uri) {
                                            //Up Post with Image

                                            mDatabase.child(USER).child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                                                    String name = (String) map.get("name");
                                                    String avatar = (String) map.get("avatar");

                                                    Post post = new Post(user.getUid(), name, day, content, uri.toString(), avatar);
                                                    mDatabase.child(POST).child(day).setValue(post)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    callback.postListener(null);
                                                                }
                                                            }) //setValue để thêm node
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    callback.postListener(e);
                                                                }
                                                            });
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            callback.postListener(exception);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.postListener(e);

                        }
                    });
        } else {
            mDatabase.child(USER).child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    String name = (String) map.get("name");
                    String avatar = (String) map.get("avatar");

                    Post post = new Post(user.getUid(), name, day, content, null, avatar);
                    mDatabase.child(POST).child(day).setValue(post)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    callback.postListener(null);
                                }
                            }) //setValue để thêm node
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    callback.postListener(e);
                                }
                            });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void loadPersonalPost(final ListPostCallback callback) {
        mDatabase.child(POST).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String id = (String) map.get("id");
                    String name = (String) map.get("name");
                    String timePost = (String) map.get("timePost");
                    String postBody = (String) map.get("post");
                    String image = (String) map.get("image");
                    String avatar = (String) map.get("avatar");

                    if (id.equals(user.getUid())) {
                        Post post = new Post(id, name, timePost, postBody, image, avatar);
                        postList.add(post);
                    }
                }
                callback.listPost(null, postList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.listPost(databaseError, null);
            }
        });
    }

    @Override
    public void loadAllPost(final ListPostCallback callback) {
        mDatabase.child(POST).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String id = (String) map.get("id");
                    String name = (String) map.get("name");
                    String timePost = (String) map.get("timePost");
                    String postBody = (String) map.get("post");
                    String image = (String) map.get("image");
                    String avatar = (String) map.get("avatar");

                    Post post = new Post(id, name, timePost, postBody, image, avatar);
                    postList.add(post);
                }

                callback.listPost(null, postList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.listPost(null, null);
            }
        });
    }

    @Override
    public void deletePost(final Post post, final DeletePostCallback callback) {
        mDatabase.child(POST).child(post.getTimePost()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Delete Image in storage
                if (!TextUtils.isEmpty(post.getImage())) {
                    StorageReference photoRef = storage.getReferenceFromUrl(post.getImage());
                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            callback.listPost(null);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            callback.listPost(exception);
                        }
                    });
                } else {
                    callback.listPost(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.listPost(e);
            }
        });
    }
}
