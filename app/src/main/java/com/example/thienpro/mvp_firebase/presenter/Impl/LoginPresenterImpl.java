package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.LoginPresenter;
import com.example.thienpro.mvp_firebase.view.LoginView;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class LoginPresenterImpl extends BasePresentermpl<LoginView> implements LoginPresenter {
    private UserInteractor userInteractor;

    public LoginPresenterImpl(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    public void signedInCheck() {
        userInteractor.signedInCheck(new UserInteractor.BooleanCheckCallback() {
            @Override
            public void onFinish(boolean checker) {
                if (getView() == null)
                    return;
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
        if (getView() == null)
            return;
        getView().showLoadingDialog();

        userInteractor.sigIn(email, password, new UserInteractor.ExceptionCheckCallback() {
            @Override
            public void onFinish(Exception e) {
                if (getView() == null)
                    return;
                getView().hideLoadingDialog();
                if (e == null) {
                    getView().navigationToHome();
                } else {
                    getView().showExceptionError(e);
                }
            }
        });
    }

    @Override
    public void forgotPassword(final String email) {
        if (getView() == null)
            return;
        getView().showLoadingDialog();

        userInteractor.forgotPassword(email, new UserInteractor.ExceptionCheckCallback() {
            @Override
            public void onFinish(Exception e) {
                if (getView() == null)
                    return;
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
