package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.ChangePasswordPresenter;
import com.example.thienpro.mvp_firebase.view.ChangePasswordView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

public class ChangePasswordImpl extends BasePresentermpl<ChangePasswordView> implements ChangePasswordPresenter {
    private UserInteractor userInteractor;

    public ChangePasswordImpl(Context context) {
        this.userInteractor = new UserInteractorImpl(context);
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
                    getView().showMessenger("Thay đổi mật khẩu thành công!");
                }
            }
        });
    }
}
