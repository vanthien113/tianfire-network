package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.UserLocationView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

public interface UserLocationPresenter extends BasePresenter<UserLocationView> {
    void getUserLocation(final String userId);

    void stopGetUserLocation();
}
