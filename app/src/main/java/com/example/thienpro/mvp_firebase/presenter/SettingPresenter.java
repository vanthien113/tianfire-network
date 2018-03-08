package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.SettingView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface SettingPresenter extends BasePresenter<SettingView> {
    void logOut();
}
