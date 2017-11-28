package com.example.thienpro.mvp_firebase.view;

import android.content.Context;

/**
 * Created by ThienPro on 11/9/2017.
 */

public interface LoginView {
    void onLoginFail(Context context);

    void navigationToHome(Context context);

    void navigationToRegister();

    void onLoginClick();

    void onRegisterClick();
}
