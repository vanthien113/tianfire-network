package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.LoginPresenter;
import com.example.thienpro.mvp_firebase.view.LoginView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class LoginPresenterImpl extends BasePresentermpl<LoginView> implements LoginPresenter {
    private UserInteractor userInteractor;

    public LoginPresenterImpl(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    public void signedInCheck() {
        userInteractor.signedInCheck(new UserInteractor.LoggedInCheckCallback() {
            @Override
            public void checker(boolean checker) {
                getView().hideLoadingDialog();
                if (checker) {
                    getView().navigationToHome();
                } else {
                    getView().navigationToVerifiEmail();
                }
            }
        });
    }

    public void onSignIn(String email, String password) {
        getView().showLoadingDialog();

        userInteractor.sigIn(email, password, new UserInteractor.LoginCheckCallback() {
            @Override
            public void checker(boolean checker, Exception e) {
                getView().hideLoadingDialog();
                if (checker) {
                    getView().navigationToHome();
                } else {
                    getView().showExceptionError(e);
                }
            }
        });
    }

    @Override
    public void forgotPassword(final String email) {
        getView().showLoadingDialog();

        userInteractor.forgotPassword(email, new UserInteractor.ChangePasswordCallback() {
            @Override
            public void changePasswordCallback(Exception e) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showLoginMessage(email);
                }
            }
        });
    }

}
