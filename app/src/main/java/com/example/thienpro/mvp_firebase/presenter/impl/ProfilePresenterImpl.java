package com.example.thienpro.mvp_firebase.presenter.impl;

import android.os.Handler;
import android.util.Log;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/16/2017.
 */

public class ProfilePresenterImpl implements ProfilePresenter {
    private DatabaseReference mDatabase;
    private ArrayList<String> time;

    public ProfilePresenterImpl(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
        time = new ArrayList<>();
    }

    @Override
    public void writeNewPost(String id, String today, String content) {
        Post post = new Post(id, today, content);
        mDatabase.child("posts").child(today).setValue(post); //setValue để thêm node
    }

    @Override
    public ArrayList<String> getTime() {//Lấy toàn bộ danh sách trong node post

        mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    time.add(String.valueOf(dsp.getValue())); //add result into array list
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(runnable, 3000);
        Log.e("THIEN", "ListTime Profile Iplm " + time.size());
        return time;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Log.e("THIEN", "ListTime Profile Iplm " + time.size());
        }
    };

}
