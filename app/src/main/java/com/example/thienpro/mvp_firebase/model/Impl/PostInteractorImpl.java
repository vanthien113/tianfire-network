package com.example.thienpro.mvp_firebase.model.Impl;

import android.text.TextUtils;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ThienPro on 11/17/2017.
 */

public class PostInteractorImpl extends BaseInteractorImpl implements PostInteractor {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public PostInteractorImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public void writeNewPost(String userName, String avatar, String content, String filePath, String currentTime, ExceptionCallback callback) {
        mDatabase.child(POSTS).child(currentTime).setValue(new Post(user.getUid(), userName, currentTime, content, filePath, avatar))
                .addOnSuccessListener(task -> callback.onFinish(null))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    @Override
    public void loadPersonalPost(String userId, ListPostCallback callback) {
        mDatabase.child(POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Post> posts = new ArrayList<>();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Post post = dsp.getValue(Post.class);
                    if (post.getId().equals(userId)) {
                        posts.add(post);
                    }
                }
                callback.onFinish(null, posts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFinish(databaseError, null);
            }
        });
    }

    @Override
    public void loadAllPost(ListPostCallback callback) {
        mDatabase.child(POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Post> posts = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Post post = dsp.getValue(Post.class);
                    posts.add(post);
                }

                callback.onFinish(null, posts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFinish(databaseError, null);
            }
        });
    }

    @Override
    public void deletePost(Post post, ExceptionCallback callback) {
        mDatabase.child(POSTS).child(post.getTimePost()).removeValue()
                .addOnSuccessListener(task -> {
                    //Delete Image in storage
                    deleteImage(post.getImage(), callback);
                }).addOnFailureListener(e -> callback.onFinish(e));
    }

    private void deleteImage(String imageUrl, ExceptionCallback callback) {
        if (!TextUtils.isEmpty(imageUrl)) {
            StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
            photoRef.delete().addOnSuccessListener(aVoid -> callback.onFinish(null)).addOnFailureListener(exception -> callback.onFinish(exception));
        } else {
            callback.onFinish(null);
        }
    }

    @Override
    public void editPost(Post post, ExceptionCallback callback) {
        Map<String, Object> postValues = post.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(post.getTimePost(), postValues);

        mDatabase.child(POSTS).updateChildren(childUpdates)
                .addOnSuccessListener(task -> callback.onFinish(null))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    @Override
    public void getPicture(String userId, GetPictureCallback callback) {
        mDatabase.child(POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> listPicture = new ArrayList<>();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String id = (String) map.get(ID);
                    String image = (String) map.get(IMAGE);

                    if (userId.equals(id) && !TextUtils.isEmpty(image)) {
                        listPicture.add(image);
                    }
                }

                Collections.reverse(listPicture);
                callback.onFinish(null, listPicture);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFinish(databaseError, null);
            }
        });
    }

    @Override
    public void getFriendPost(String userId, ListPostCallback callback) {
        mDatabase.child(POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Post> listPost = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Post post = dsp.getValue(Post.class);
                    if (TextUtils.equals(post.getId(), userId)) {
                        listPost.add(post);
                    }
                }

                Collections.reverse(listPost);
                callback.onFinish(null, listPost);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFinish(databaseError, null);
            }
        });
    }
}
