package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.RegistrerPresenter;
import com.example.thienpro.mvp_firebase.view.RegisterView;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class RegisterPresenterImpl implements UserInteractor.userListener, RegistrerPresenter {
    private UserInteractor userInteractor;
    private RegisterView registerView;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        userInteractor = new UserInteractorImpl(this);
    }

    public void register(String email, String password, String name, String address, boolean sex, Uri filePath) {
        userInteractor.register(email, password, name, address, sex, filePath);
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
        registerView.navigationToVerifiEmail();
    }

    @Override
    public void navigationToLogin() {

    }

    @Override
    public void onRegisterFail() {
        registerView.onRegisterEmailFail();
    }

    @Override
    public void onLoginFail() {

    }

    @Override
    public void navigationToVerifiEmail() {
        registerView.navigationToVerifiEmail();
    }
}
