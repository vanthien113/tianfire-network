package com.example.thienpro.mvp_firebase.presenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface EditInfoPresenter {
    void loadUser();
    void updateUser(String email, String name, String address, boolean sex, final String avatar);
}
