package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.ChangePasswordPresenter;
import com.example.thienpro.mvp_firebase.view.ChangePasswordView;

public class ChangePasswordPresenterImpl extends BasePresentermpl<ChangePasswordView> implements ChangePasswordPresenter {
    private UserInteractor userInteractor;

    public ChangePasswordPresenterImpl(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    @Override
    public void changePassword(String password) {
        if (getView() == null)
            return;
        getView().showLoadingDialog();
        userInteractor.changePassword(password, new UserInteractor.ExceptionCheckCallback() {
            @Override
            public void onFinish(Exception e) {
                if (getView() == null)
                    return;
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
