package com.example.thienpro.mvp_firebase.presenter;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface RegisterPresenter {

    void writeNewUser(String userId, String email, String name, String address, Boolean sex);
    void updateUser(String userId, String email, String name, String address, Boolean sex);
}
