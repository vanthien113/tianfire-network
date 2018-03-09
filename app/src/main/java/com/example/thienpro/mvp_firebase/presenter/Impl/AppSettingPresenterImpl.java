package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.location.Location;

import com.example.thienpro.mvp_firebase.model.Impl.LocationInteractorImpl;
import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.presenter.AppSettingPresenter;
import com.example.thienpro.mvp_firebase.ultils.SHLocationManager;
import com.example.thienpro.mvp_firebase.view.AppSettingView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppSettingPresenterImpl extends BasePresentermpl<AppSettingView> implements AppSettingPresenter {
    private LocationInteractor locationInteractor;
    private Location location;
    private static ScheduledExecutorService scheduledExecutorService;
    private UserInteractor userInteractor;

    public AppSettingPresenterImpl(Context context) {
        this.locationInteractor = new LocationInteractorImpl(context);
        this.location = SHLocationManager.getLastKnowLocation(context);
        this.userInteractor = new UserInteractorImpl(context);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void pushLocation(final boolean status) {
        if (scheduledExecutorService.isShutdown()) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }
        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserCallback() {
            @Override
            public void currentLocalUser(final User user) {
                scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
                    @Override
                    public void run() {
                        final UserLocation currentLocation = new UserLocation(user.getName(), null, location.getLongitude(), location.getLatitude(), status);
                        locationInteractor.pushLocation(currentLocation, new LocationInteractor.PushLocationCallback() {
                            @Override
                            public void pushLocation(Exception e) {
                                if (e != null) {
                                    getView().showExceptionError(e);
                                } else {
                                    if (!status) {
                                        stopPushLocation();
                                    }
                                }
                            }
                        });
                    }
                }, 0, 5, TimeUnit.SECONDS);
            }
        });
    }

    @Override
    public void stopPushLocation() {
        scheduledExecutorService.shutdownNow();
    }

    @Override
    public void checkShareLocation() {
        locationInteractor.getShareLocation(new LocationInteractor.GetShareLocationCallback() {
            @Override
            public void getShareLocation(boolean isShare) {
                getView().shareLocation(isShare);
            }
        });
    }

    @Override
    public void saveShareLocation(boolean isShared) {
        locationInteractor.saveShareLocation(isShared);
    }
}
