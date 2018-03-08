package com.example.thienpro.mvp_firebase.presenter;

public interface AppSettingPresenter {
    void pushLocation(boolean status);

    void getLocation();

    void stopPushLocation();

    void getListLocation();

    void checkShareLocation();

    void saveShareLocation(boolean isShared);
}
