package com.example.thienpro.mvp_firebase.presenter;

public interface UserLocationPresenter {
    void getUserLocation(final String userId);

    void stopGetUserLocation();
}
