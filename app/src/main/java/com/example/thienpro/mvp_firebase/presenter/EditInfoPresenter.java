package com.example.thienpro.mvp_firebase.presenter;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.LoadUserListener;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.EditInfoView;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class EditInfoPresenter implements LoadUserListener {
    private EditInfoView editInfoView;
    private UserInteractor userInteractor;

    public EditInfoPresenter(EditInfoView editInfoView) {
        this.editInfoView = editInfoView;
        userInteractor = new UserInteractor(this);
    }

    public void loadUser() {
        userInteractor.getUser();
    }

    public void updateUser(String email, String name, String address, boolean sex) {
        userInteractor.updateUser(email, name, address, sex);
    }

    @Override
    public void getUser(User user) {
        editInfoView.getUser(user);
    }

    @Override
    public void navigationToHome() {

    }

    @Override
    public void onRegisterFail() {

    }

    @Override
    public void onLoginFail() {

    }
}
