package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.SettingPresenter;
import com.example.thienpro.mvp_firebase.view.SettingView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class SettingPresenterImpl extends BasePresentermpl<SettingView> implements SettingPresenter {
    private UserInteractor userInteractor;
    private UserManager userManager;

    public SettingPresenterImpl(UserInteractor userInteractor, UserManager userManager) {
        this.userInteractor = userInteractor;
        this.userManager = userManager;
    }

    @Override
    public void logOut() {
        userInteractor.logOut();
        getView().navigationToLogin();
        userManager.logout();
    }
}
