package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.net.Uri;
import android.util.Log;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.ChangeAvatarPresenter;
import com.example.thienpro.mvp_firebase.view.ChangeAvatarView;

/**
 * Created by vanthien113 on 1/13/2018.
 */

public class ChangeAvatarPresenterImpl implements ChangeAvatarPresenter, UserInteractor.userListener {
    private ChangeAvatarView changeAvatarView;
    private UserInteractor userInteractor;
    private Uri uri;

    public ChangeAvatarPresenterImpl(ChangeAvatarView changeAvatarView) {
        this.changeAvatarView = changeAvatarView;
        userInteractor = new UserInteractorImpl(this);
    }

    @Override
    public void sendVerifiEmailComplete(String email) {

    }

    @Override
    public void sendVerifiEmailFail(String email) {

    }

    @Override
    public void getUser(User user) {
        userInteractor.addAvatar(user.getEmail(), user.getName(), user.getAddress(), user.getSex(), uri);
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

    @Override
    public void changeAvatar(Uri uri) {
        this.uri = uri;
        userInteractor.getUser();
    }
}
