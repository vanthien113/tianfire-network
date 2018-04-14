package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.AppSettingPresenter;
import com.example.thienpro.mvp_firebase.view.AppSettingView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppSettingPresenterImpl extends BasePresentermpl<AppSettingView> implements AppSettingPresenter {
    private LocationInteractor locationInteractor;
    private static ScheduledExecutorService scheduledExecutorService;
    private UserInteractor userInteractor;

    public AppSettingPresenterImpl(LocationInteractor locationInteractor, UserInteractor userInteractor) {
        this.locationInteractor = locationInteractor;
        this.userInteractor = userInteractor;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void pushLocation(final boolean status) {
        if (scheduledExecutorService.isShutdown()) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }
//            @Override
//            public void currentLocalUser(final User user) {
//                scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//                    @Override
//                    public void run() {
//                        getLocation(user, status);
//                    }
//                }, 0, 5, TimeUnit.SECONDS);
//            }
//        });
    }

    private void getLocation(final User user, final boolean status) {
//        SHLocationManager.getCurrentLocation(context, new SHLocationManager.OnCurrentLocationCallback() {
//            @Override
//            public void callback(Location location) {
//                final UserLocation currentLocation = new UserLocation(user.getName(), null, location.getLongitude(), location.getLatitude(), status);
//
//                locationInteractor.pushLocation(currentLocation, new LocationInteractor.PushLocationCallback() {
//                    @Override
//                    public void pushLocation(Exception e) {
//                        if (e != null) {
//                            getView().showExceptionError(e);
//                        } else {
//                            if (!status) {
//                                stopPushLocation();
//                            }
//                        }
//                    }
//                });
//            }
//        });
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
