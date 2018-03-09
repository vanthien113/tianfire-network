package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.LoginPresenter;
import com.example.thienpro.mvp_firebase.view.LoginView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class LoginPresenterImpl extends BasePresentermpl<LoginView> implements LoginPresenter {
    private UserInteractor userInteractor;

    public LoginPresenterImpl(Context context) {
        this.userInteractor = new UserInteractorImpl(context);
    }

    public void signedInCheck() {
        getView().showLoadingDialog();

        userInteractor.signedInCheck(new UserInteractor.LoggedInCheckCallback() {
            @Override
            public void checker(int checker) {
                getView().hideLoadingDialog();
                switch (checker) {
                    case 1:
                        getView().navigationToHome();
                        break;
                    case 2:
                        getView().navigationToVerifiEmail();
                        break;
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
                    getView().navigationToVerifiEmail();
                } else {
                    getView().showExceptionError(e);
                }
            }
        });
    }

    @Override
    public void onForgotPasswordClick(final String email) {
        getView().showLoadingDialog();

        userInteractor.forgotPassword(email, new UserInteractor.ChangePasswordCallback() {
            @Override
            public void changePasswordCallback(Exception e) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showMessenger("Email chứa link thay đổi mật khẩu đã được gửi về địa chỉ email " + email);
                }
            }
        });
    }

}
