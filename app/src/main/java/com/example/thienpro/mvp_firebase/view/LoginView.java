package com.example.thienpro.mvp_firebase.view;

import android.content.Context;

/**
 * Created by ThienPro on 11/9/2017.
 */

public interface LoginView {
    void onLoginFail();

    void navigationToHome();

    void onLoginClick();

    void onRegisterClick();

    void navigationToLogin();

    void navigationToVerifiEmail();
}
