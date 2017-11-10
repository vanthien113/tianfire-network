package com.example.thienpro.mvp_firebase.view;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ThienPro on 11/9/2017.
 */

public interface MainView {
    void onLoginFail();

    void navigationToHome(Context context, FirebaseUser user);

    void navigationToRegister();

    void onLoginClick();

    void onRegisterClick();
}
