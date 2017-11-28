package com.example.thienpro.mvp_firebase.model.Impl;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by ThienPro on 11/17/2017.
 */

public class PostInteractorImpl implements PostInteractor{
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private LoadPostListener loadPostListener;
    private ArrayList<Post> postList;
    private Date today;
    private String day;
    private SimpleDateFormat simpleDateFormat;

    public PostInteractorImpl(LoadPostListener loadPostListener) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        postList = new ArrayList<>();
        this.loadPostListener = loadPostListener;
    }

    public void writeNewPost(final String content) {
        today = new Date();
        today.getDate();
        simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        day = simpleDateFormat.format(today);

        mDatabase.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String name = (String) map.get("name");
                Post post = new Post(user.getUid(), name, day, content);
                mDatabase.child("posts").child(day).setValue(post); //setValue để thêm node
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void LoadPersonalPost() {
        mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String firstValue = (String) map.get("id");
                    String secondValue = (String) map.get("name");
                    String thirdValue = (String) map.get("timePost");
                    String foureValue = (String) map.get("post");

                    if (firstValue.equals(user.getUid().toString())) {
                        Post post = new Post(firstValue, secondValue, thirdValue, foureValue);
                        postList.add(post);
                    }
                }
                loadPostListener.ListPost(postList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void loadAllPost() {
        mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String firstValue = (String) map.get("id");
                    String secondValue = (String) map.get("name");
                    String thirdValue = (String) map.get("timePost");
                    String foureValue = (String) map.get("post");

                    Post post = new Post(firstValue, secondValue, thirdValue, foureValue);
                    postList.add(post);

                }
                loadPostListener.ListPost(postList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
