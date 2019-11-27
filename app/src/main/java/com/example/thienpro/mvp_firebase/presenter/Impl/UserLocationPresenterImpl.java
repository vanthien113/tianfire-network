package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.presenter.UserLocationPresenter;
import com.example.thienpro.mvp_firebase.view.UserLocationView;

public class UserLocationPresenterImpl extends BasePresentermpl<UserLocationView> implements UserLocationPresenter {
    private LocationInteractor interactor;

    public UserLocationPresenterImpl(LocationInteractor locationInteractor) {
        this.interactor = locationInteractor;
    }

    @Override
    public void getUserLocation(final String userId) {
        interactor.getLocation(userId, (e, location) -> {
            if (getView() == null)
                return;
            if (e != null) {
                getView().showDatabaseError(e);
            } else {
                getView().showUserLocation(location);
            }
        });
    }

    public void stopGetUserLocation() {
    }
}
