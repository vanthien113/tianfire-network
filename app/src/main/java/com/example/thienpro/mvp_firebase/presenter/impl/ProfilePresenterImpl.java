package com.example.thienpro.mvp_firebase.presenter.impl;

import android.os.Handler;
import android.util.Log;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
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

    public ProfilePresenterImpl(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    @Override
    public void writeNewPost(String id, String name, String today, String content) {
        Post post = new Post(id, name, today, content);
        mDatabase.child("posts").child(today).setValue(post); //setValue để thêm node
    }
}
