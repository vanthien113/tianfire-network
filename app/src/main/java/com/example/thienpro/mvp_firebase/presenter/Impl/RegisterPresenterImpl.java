package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.RegistrerPresenter;
import com.example.thienpro.mvp_firebase.view.RegisterView;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class RegisterPresenterImpl implements UserInteractor.LoadUserListener, RegistrerPresenter {
    private UserInteractor userInteractor;
    private RegisterView registerView;
    private Context context;

    public RegisterPresenterImpl(RegisterView registerView, Context context) {
        this.registerView = registerView;
        userInteractor = new UserInteractorImpl(this);
        this.context = context;
    }

    public void register(String email, String password, String name, String address, boolean sex) {
        userInteractor.register(email, password, name, address, sex);
    }

    @Override
    public void getUser(User user) {
    }

    @Override
    public void navigationToHome() {
        registerView.navigationToHome(context);
    }

    @Override
    public void onRegisterFail() {
        registerView.onRegisterEmailFail(context);
    }

    @Override
    public void onLoginFail() {

    }
}
