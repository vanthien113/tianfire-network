package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.view.bases.BaseView;

public interface AppSettingView extends BaseView{
    void onCheckLocationClick();

    void onShowListLocationClick();

    void shareLocation(boolean isShared);
}
