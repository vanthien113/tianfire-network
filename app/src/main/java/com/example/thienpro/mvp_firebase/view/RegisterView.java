package com.example.thienpro.mvp_firebase.view;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.entity.User;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface RegisterView {
    void onRegisterClick();

    void navigationToHome(Context context);

    void onRegisterFail(Context context);

    void onRegisterNull(Context context);

    void onRegisterEmailFail(Context context);
}
