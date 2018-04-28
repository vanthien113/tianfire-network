package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.LoginView;
import com.example.thienpro.mvp_firebase.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface LoginPresenter extends BasePresenter<LoginView> {
    void signedInCheck();

    void onSignIn(String email, String password);

    void forgotPassword(String email);

    void getUserInfo();
}
