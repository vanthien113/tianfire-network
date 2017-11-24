package com.example.thienpro.mvp_firebase.presenter;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.LoadUserListener;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.LoginView;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class LoginPresenter implements LoadUserListener {
    private UserInteractor userInteractor;
    private LoginView loginView;
    private Context context;

    public LoginPresenter(LoginView loginView, Context context) {
        this.userInteractor = new UserInteractor(this, context);
        this.loginView = loginView;
        this.context = context;
    }

    public void onSignIn(String email, String password){
        userInteractor.Sigin(email, password);
    }

    @Override
    public void getUser(User user) {

    }

    @Override
    public void navigationToHome() {
        loginView.navigationToHome(context);
    }

    @Override
    public void onRegisterFail() {

    }

    @Override
    public void onSignInNull() {
        loginView.onSignInNull((Context) loginView);
    }

    @Override
    public void onLoginFail() {
        loginView.onLoginFail((Context) loginView);
    }
}
