package com.example.thienpro.mvp_firebase.model.Impl;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
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

    @Override
    public void writeNewPost(final String content, final Uri filePath) {
        today = new Date();
        today.getDate();
        simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        day = simpleDateFormat.format(today);

        uploadImage(content, filePath);
    }

    private void uploadImage(final String content, Uri filePath) {
        if (filePath != null) {
            ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.e("THIEN", "UPLOAD OKE");
                            getUrlImage(content);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            onFailure(e);
                            Log.e("THIEN", "UPLOAD NOT OKE");

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            post(content, null);
        }
    }

    private void getUrlImage(final String content) {
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                post(content, uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.e("THIEN", "GET NOT OKE");
            }
        });
    }

    private void post(final String content, final String url) {
        mDatabase.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String name = (String) map.get("name");
                String avatar = (String) map.get("avatar");

                Post post = new Post(user.getUid(), name, day, content, url, avatar);
                mDatabase.child("posts").child(day).setValue(post); //setValue để thêm node
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void loadPersonalPost(final ListPost listPost) {
        mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String firstValue = (String) map.get("id");
                    String secondValue = (String) map.get("name");
                    String thirdValue = (String) map.get("timePost");
                    String foureValue = (String) map.get("post");
                    String fiveValue = (String) map.get("image");
                    String sixValue = (String) map.get("avatar");

                    if (firstValue.equals(user.getUid().toString())) {
                        Post post = new Post(firstValue, secondValue, thirdValue, foureValue, fiveValue, sixValue);
                        postList.add(post);
                    }
                }
                listPost.listPost(null, postList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listPost.listPost(databaseError, null);
            }
        });
    }

    @Override
    public void loadAllPost(final ListPost listPost) {
        mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String firstValue = (String) map.get("id");
                    String secondValue = (String) map.get("name");
                    String thirdValue = (String) map.get("timePost");
                    String foureValue = (String) map.get("post");
                    String fiveValue = (String) map.get("image");
                    String sixValue = (String) map.get("avatar");

                    Post post = new Post(firstValue, secondValue, thirdValue, foureValue, fiveValue, sixValue);
                    postList.add(post);
                }

                listPost.listPost(null, postList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listPost.listPost(null, null);
            }
        });
    }
}
