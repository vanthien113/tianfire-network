package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.view.bases.BaseView;

/**
 * Created by ThienPro on 11/23/2017.
 */

public interface SettingView extends BaseView{
    void onEditInfoClick();

    void onLogout();

    void navigationToLogin();

    void onAppSettingClick();

    void onChangePasswordClick();
}
