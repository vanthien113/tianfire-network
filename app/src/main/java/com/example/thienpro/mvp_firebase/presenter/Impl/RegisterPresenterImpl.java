package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.RegistrerPresenter;
import com.example.thienpro.mvp_firebase.view.RegisterView;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class RegisterPresenterImpl implements RegistrerPresenter {
    private RegisterView view;
    private UserInteractor userInteractor;

    public RegisterPresenterImpl(RegisterView registerView, Context context) {
        this.view = registerView;
        this.userInteractor = new UserInteractorImpl(context);
    }

    @Override
    public void register(String email, String password, String name, String address, boolean sex) {
        userInteractor.register(email, password, name, address, sex, new UserInteractor.RegisterCheckCallback() {
            @Override
            public void checker(Exception checker) {
                if (checker == null) {
                    view.navigationToVerifiEmail();
                } else {
                    view.onRegisterFail(checker);
                }
            }
        });
    }
}
