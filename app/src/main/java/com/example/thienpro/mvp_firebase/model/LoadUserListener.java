package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.User;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface LoadUserListener {
    void getUser(User user);

    void navigationToHome();

    void onRegisterFail();

    void onLoginFail();
}
