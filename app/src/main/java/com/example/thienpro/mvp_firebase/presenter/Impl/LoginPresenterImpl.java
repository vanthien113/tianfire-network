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
    private LoginView view;

    public LoginPresenterImpl(LoginView loginView) {
        this.userInteractor = new UserInteractorImpl();
        this.view = loginView;
    }

    public void signedInCheck() {
        view.showLoading();

        userInteractor.signedInCheck(new UserInteractor.LoggedInCheck() {
            @Override
            public void checker(int checker) {
                view.hideLoading();
                switch (checker) {
                    case 1:
                        view.navigationToHome();
                        break;
                    case 2:
                        view.navigationToVerifiEmail();
                        break;
                }
            }
        });
    }

    public void onSignIn(String email, String password) {
        view.showLoading();

        userInteractor.sigIn(email, password, new UserInteractor.LoginCheck() {
            @Override
            public void checker(boolean checker, Exception e) {
                if (checker) {
                    view.hideLoading();
                    view.navigationToVerifiEmail();
                } else {
                    view.hideLoading();
                    view.onLoginFail(e);

//                    view.navigationToLogin();
                }
            }
        });
    }
}
