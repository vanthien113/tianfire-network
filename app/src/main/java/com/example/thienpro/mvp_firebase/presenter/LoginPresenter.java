package com.example.thienpro.mvp_firebase.presenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface LoginPresenter {
    void signedInCheck();
    void onSignIn(String email, String password);
}
