package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.AppSettingView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

public interface AppSettingPresenter extends BasePresenter<AppSettingView> {
    void pushLocation(boolean status);

    void getLocation();

    void stopPushLocation();

    void getListLocation();

    void checkShareLocation();

    void saveShareLocation(boolean isShared);
}
