package com.example.thienpro.mvp_firebase.view;

public interface AppSettingView {
    void onCheckLocationClick();

    void showError(Exception e);

    void onShowListLocationClick();

    void shareLocation(boolean isShared);
}
