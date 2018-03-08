package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.LocationInteractorImpl;
import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.presenter.UserLocationPresenter;
import com.example.thienpro.mvp_firebase.view.UserLocationView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserLocationPresenterImpl extends BasePresentermpl<UserLocationView> implements UserLocationPresenter {
    private LocationInteractor interactor;
    private static ScheduledExecutorService scheduledExecutorService;

    public UserLocationPresenterImpl(Context context) {
        this.interactor = new LocationInteractorImpl(context);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void getUserLocation(final String userId) {
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                interactor.getLocation(userId, new LocationInteractor.GetLocationCallback() {
                    @Override
                    public void getLocation(DatabaseError e, UserLocation location) {
                        if (e != null) {

                        } else {
                            getView().showUserLocation(location);
                        }
                    }
                });
            }
        }, 0, 5, TimeUnit.SECONDS);

    }

    public void stopGetUserLocation() {
        scheduledExecutorService.shutdownNow();
    }
}
