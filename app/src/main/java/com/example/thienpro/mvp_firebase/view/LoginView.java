package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.view.bases.BaseView;

/**
 * Created by ThienPro on 11/9/2017.
 */

public interface LoginView extends BaseView {
    void navigationToHome();

    void onLoginClick();

    void onRegisterClick();

    void navigationToLogin();

    void navigationToVerifiEmail();

    void onShowPasswordClick();

    void onForgotPasswordClick();

    void showLoginMessage(String email);
}
