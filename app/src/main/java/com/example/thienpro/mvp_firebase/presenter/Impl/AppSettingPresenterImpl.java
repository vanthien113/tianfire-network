package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.location.Location;

import com.example.thienpro.mvp_firebase.manager.LocationManager;
import com.example.thienpro.mvp_firebase.manager.UserManager;
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
    private static ScheduledExecutorService scheduledExecutorService;
    private UserInteractor userInteractor;
    private LocationManager locationManager;
    private UserManager userManager;

    public AppSettingPresenterImpl(LocationInteractor locationInteractor, LocationManager locationManager, UserManager userManager) {
        this.locationInteractor = locationInteractor;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.locationManager = locationManager;
        this.userManager = userManager;
    }

    @Override
    public void pushLocation(final Context context, final boolean status) {
        if (scheduledExecutorService.isShutdown()) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                getLocation(context, userManager.getCurrentUser(), status);
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void getLocation(Context context, final User user, final boolean status) {
        SHLocationManager.getCurrentLocation(context, new SHLocationManager.OnCurrentLocationCallback() {
            @Override
            public void callback(Location location) {
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
        });
    }

    @Override
    public void stopPushLocation() {
        scheduledExecutorService.shutdownNow();
    }

    @Override
    public boolean checkShareLocation() {
        return locationManager.checkShareLocation();
    }

    @Override
    public void saveShareLocation(boolean isShared) {
        locationManager.shareLocation(isShared);
    }
}
