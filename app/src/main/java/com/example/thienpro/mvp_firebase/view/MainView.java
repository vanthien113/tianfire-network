package com.example.thienpro.mvp_firebase.view;

/**
 * Created by ThienPro on 11/9/2017.
 */

public interface MainView {
    void onLoginFail();

    void navigationToHome();

    void navigationToRegister();

    void onLoginClick();

    void onRegisterClick();
}
