package com.example.thienpro.mvp_firebase.presenter.impl;

import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.RegisterPresenter;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class RegisterPresenterImpl implements RegisterPresenter {
    private DatabaseReference mDatabase;
    public RegisterPresenterImpl(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    @Override
    public void writeNewUser(String userId, String email, String name, String address, Boolean sex) {
        User user = new User(email, name, address, sex);
        mDatabase.child("users").child(userId).setValue(user); //setValue để thêm node
    }

    @Override
    public void updateUser(String userId, String email, String name, String address, Boolean sex) {
        User user = new User(email, name, address, sex);
        Map<String, Object> postValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userId, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
