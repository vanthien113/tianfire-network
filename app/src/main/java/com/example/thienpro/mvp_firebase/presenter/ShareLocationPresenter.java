package com.example.thienpro.mvp_firebase.presenter;

import android.content.Context;

import com.example.thienpro.mvp_firebase.view.ShareLocationView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

public interface ShareLocationPresenter extends BasePresenter<ShareLocationView> {
    void pushLocation(final Context context);

    void stopPushLocation();

    void getListLocation();

}
