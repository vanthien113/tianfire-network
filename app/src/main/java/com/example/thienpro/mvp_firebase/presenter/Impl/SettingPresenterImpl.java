package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.SettingPresenter;
import com.example.thienpro.mvp_firebase.view.SettingView;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class SettingPresenterImpl implements SettingPresenter, UserInteractor.userListener {
    private UserInteractor userInteractor;

    public SettingPresenterImpl(SettingView settingView) {
        this.userInteractor = new UserInteractorImpl(this);
    }

    @Override
    public void logOut() {
        userInteractor.logOut();
    }

    @Override
    public void sendVerifiEmailComplete(String email) {

    }

    @Override
    public void sendVerifiEmailFail(String email) {

    }

    @Override
    public void getUser(User user) {

    }

    @Override
    public void navigationToHome() {

    }

    @Override
    public void navigationToLogin() {
    }

    @Override
    public void onRegisterFail() {

    }

    @Override
    public void onLoginFail() {

    }

    @Override
    public void navigationToVerifiEmail() {

    }
}
