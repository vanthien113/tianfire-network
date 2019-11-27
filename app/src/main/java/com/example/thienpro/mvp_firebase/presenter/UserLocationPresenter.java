package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.bases.BasePresenter;
import com.example.thienpro.mvp_firebase.view.UserLocationView;

public interface UserLocationPresenter extends BasePresenter<UserLocationView> {
    void getUserLocation(final String userId);
}
