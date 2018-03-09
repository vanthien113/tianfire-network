package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.SettingPresenter;
import com.example.thienpro.mvp_firebase.view.SettingView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class SettingPresenterImpl extends BasePresentermpl<SettingView> implements SettingPresenter {
    private UserInteractor userInteractor;

    public SettingPresenterImpl(Context context) {
        this.userInteractor = new UserInteractorImpl(context);
    }

    @Override
    public void logOut() {
        userInteractor.logOut(new UserInteractor.LogoutCheckCallback() {
            @Override
            public void checker(boolean checker) {
                if (checker) {
                    getView().navigationToLogin();
                }
            }
        });
    }
}
