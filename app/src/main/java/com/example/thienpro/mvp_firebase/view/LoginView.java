package com.example.thienpro.mvp_firebase.view;

/**
 * Created by ThienPro on 11/9/2017.
 */

public interface LoginView {
    void onLoginFail(Exception e);

    void navigationToHome();

    void onLoginClick();

    void onRegisterClick();

    void navigationToLogin();

    void navigationToVerifiEmail();

    void showLoading();

    void hideLoading();
}
