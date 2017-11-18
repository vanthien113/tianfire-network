package com.example.thienpro.mvp_firebase.presenter.impl;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.google.firebase.database.DatabaseReference;

/**
 * - Presenter: Là lớp xử lý logic từ dữ liệu nhận được.
 * - Nhận dữ liệu từ lớp Model
 * - Đẩy dữ liệu lên lớp V.
 */

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
