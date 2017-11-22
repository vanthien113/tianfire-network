package com.example.thienpro.mvp_firebase.presenter;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.LoadUserListener;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.RegisterView;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class RegisterPresenterImpl implements LoadUserListener {
    private UserInteractor userInteractor;
    private RegisterView registerView;

    public RegisterPresenterImpl(RegisterView registerView, Context context) {
        this.registerView = registerView;
        userInteractor = new UserInteractor(this, context);
    }

    public void register(String email, String password, String repassword, String name, String address, boolean sex) {
        if(email.equals("") || password.equals("") || repassword.equals("") || name.equals("") || address.equals(""))
            registerView.onRegisterNull((Context) registerView);
        userInteractor.register(email, password, repassword, name, address, sex);
    }

    @Override
    public void getUser(User user) {
    }

    @Override
    public void navigationToHome() {
        registerView.navigationToHome((Context) registerView);
    }

    @Override
    public void onRegisterFail() {
        registerView.onRegisterFail((Context) registerView);
    }

    @Override
    public void onSignInNull() {

    }

    @Override
    public void onLoginFail() {

    }
}
