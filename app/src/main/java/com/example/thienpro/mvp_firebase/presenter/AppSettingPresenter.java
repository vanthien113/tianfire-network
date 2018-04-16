package com.example.thienpro.mvp_firebase.presenter;

import android.content.Context;

import com.example.thienpro.mvp_firebase.view.AppSettingView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

public interface AppSettingPresenter extends BasePresenter<AppSettingView> {
    void pushLocation(final Context context, final boolean status);

    void stopPushLocation();

    boolean checkShareLocation();

    void saveShareLocation(boolean isShared);
}
