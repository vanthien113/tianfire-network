package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.VerifiEmailPresenter;
import com.example.thienpro.mvp_firebase.view.VerifiEmailView;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class VerifiEmailPresenterImpl implements VerifiEmailPresenter {
    private UserInteractor userInteractor;
    private VerifiEmailView verifiEmailView;

    public VerifiEmailPresenterImpl(VerifiEmailView verifiEmailView) {
        this.userInteractor = new UserInteractorImpl();
        this.verifiEmailView = verifiEmailView;
    }

    @Override
    public void verifiEmail() {
        userInteractor.verifiEmail(new UserInteractor.VerifiEmailCheck() {
            @Override
            public void checker(Exception checker, String email) {
                if (checker == null && email == null) {
                    verifiEmailView.navigationToHome();
                } else if (checker == null && email != null) {
                    verifiEmailView.sendverifiEmailComplete(email);
                } else {
                    verifiEmailView.sendverifiEmailFail(email);
                }
            }
        });
    }

    @Override
    public void logOut() {
        userInteractor.logOut(new UserInteractor.LogoutCheck() {
            @Override
            public void checker(boolean checker) {
                if (checker) {
                    verifiEmailView.navigationToLogin();
                }
            }
        });
    }
}
