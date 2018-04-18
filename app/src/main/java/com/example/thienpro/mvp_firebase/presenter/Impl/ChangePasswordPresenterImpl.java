package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.ChangePasswordPresenter;
import com.example.thienpro.mvp_firebase.view.ChangePasswordView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

public class ChangePasswordPresenterImpl extends BasePresentermpl<ChangePasswordView> implements ChangePasswordPresenter {
    private UserInteractor userInteractor;

    public ChangePasswordPresenterImpl(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    @Override
    public void changePassword(String password) {
        getView().showLoadingDialog();
        userInteractor.changePassword(password, new UserInteractor.ChangePasswordCallback() {
            @Override
            public void changePasswordCallback(Exception e) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showChangePasswordComplete();
                }
            }
        });
    }
}
