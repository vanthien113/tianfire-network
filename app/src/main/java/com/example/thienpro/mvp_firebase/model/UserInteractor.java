package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.User;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface UserInteractor {

    int signedInCheck();

    void verifiEmail();

    void register(String email, String password, String name, String address, boolean sex);

    void updateUser(String email, String name, String address, Boolean sex);

    void getUser();

    void sigIn(String email, String password);

    void logOut();

    interface userListener {

        void sendVerifiEmailComplete(String email);

        void sendVerifiEmailFail(String email);

        void getUser(User user);

        void navigationToHome();

        void navigationToLogin();

        void onRegisterFail();

        void onLoginFail();

        void navigationToVerifiEmail();
    }
}
