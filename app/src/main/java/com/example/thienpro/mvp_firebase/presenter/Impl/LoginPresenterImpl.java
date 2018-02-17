package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.LoginPresenter;
import com.example.thienpro.mvp_firebase.view.LoginView;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private UserInteractor userInteractor;
    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.userInteractor = new UserInteractorImpl();
        this.loginView = loginView;
    }

    public void signedInCheck() {
        userInteractor.signedInCheck(new UserInteractor.LoginCheck() {
            @Override
            public void checker(boolean checker) {
                if (checker) {
                    loginView.navigationToHome();
                } else {
                    loginView.navigationToVerifiEmail();
                }
            }
        });
    }

    public void onSignIn(String email, String password) {
        userInteractor.sigIn(email, password, new UserInteractor.LoginCheck() {
            @Override
            public void checker(boolean checker) {
                if (checker) {
                    loginView.navigationToVerifiEmail();
                } else {
                    loginView.navigationToLogin();
                }
            }
        });
    }
}
