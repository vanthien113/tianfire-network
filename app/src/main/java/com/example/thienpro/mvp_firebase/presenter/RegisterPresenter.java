package com.example.thienpro.mvp_firebase.presenter;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.LoadUserListener;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.RegisterView;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class RegisterPresenter implements LoadUserListener {
    private UserInteractor userInteractor;
    private RegisterView registerView;
    private Context context;

    public RegisterPresenter(RegisterView registerView, Context context) {
        this.registerView = registerView;
        userInteractor = new UserInteractor(this);
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
