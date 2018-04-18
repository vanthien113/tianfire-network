package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.location.Location;

import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.presenter.ShareLocationPresenter;
import com.example.thienpro.mvp_firebase.ultils.SHLocationManager;
import com.example.thienpro.mvp_firebase.view.ShareLocationView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ShareLocationPresenterImpl extends BasePresentermpl<ShareLocationView> implements ShareLocationPresenter {
    private LocationInteractor locationInteractor;
    private static ScheduledExecutorService scheduledExecutorService;
    private UserManager userManager;
    private Date today;
    private SimpleDateFormat simpleDateFormat;

    public ShareLocationPresenterImpl(LocationInteractor locationInteractor, UserManager userManager) {
        this.locationInteractor = locationInteractor;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.userManager = userManager;
    }

    @Override
    public void pushLocation(final Context context) {
        if (scheduledExecutorService.isShutdown()) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                getLocation(context, userManager.getCurrentUser());
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void getLocation(Context context, final User user) {
        SHLocationManager.getCurrentLocation(context, new SHLocationManager.OnCurrentLocationCallback() {
            @Override
            public void callback(Location location) {
                today = new Date();
                today.getDate();
                simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
                String day = simpleDateFormat.format(today);

                final UserLocation currentLocation = new UserLocation(user.getName(), user.getId(), location.getLongitude(), location.getLatitude(), day);

                locationInteractor.pushLocation(currentLocation, new LocationInteractor.PushLocationCallback() {
                    @Override
                    public void pushLocation(Exception e) {
                        if (getView() == null)
                            return;
                        if (e != null) {
                            getView().showExceptionError(e);
                        } else {
                            getView().showSharingMessage();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void stopPushLocation() {
        scheduledExecutorService.shutdown();
        getView().showStopShareMessage();
    }

    @Override
    public void getListLocation() {
        if (getView() == null)
            return;
        getView().showLoadingDialog();
        locationInteractor.getListLocation(new LocationInteractor.GetListLocationCallback() {
            @Override
            public void listLocation(ArrayList<UserLocation> locations, DatabaseError e) {
                if (getView() == null)
                    return;
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showDatabaseError(e);
                } else {
                    getView().showListLocation(locations);
                }
            }
        });
    }
}
