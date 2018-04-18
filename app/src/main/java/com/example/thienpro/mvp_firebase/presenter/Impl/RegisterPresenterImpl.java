package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.RegisterPresenter;
import com.example.thienpro.mvp_firebase.view.RegisterView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class RegisterPresenterImpl extends BasePresentermpl<RegisterView> implements RegisterPresenter {
    private UserInteractor userInteractor;

    public RegisterPresenterImpl(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    @Override
    public void register(String email, String password, String name, String address, boolean sex) {
        userInteractor.register(email, password, name, address, sex, new UserInteractor.RegisterCheckCallback() {
            @Override
            public void checker(Exception checker) {
                if (getView() == null)
                    return;
                if (checker == null) {
                    getView().navigationToVerifiEmail();
                } else {
                    getView().showExceptionError(checker);
                }
            }
        });
    }
}
