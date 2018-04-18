package com.example.thienpro.mvp_firebase.manager.impl;

import android.content.SharedPreferences;

import com.example.thienpro.mvp_firebase.manager.LocationManager;

public class LocalLocationManager implements LocationManager {
    private static final String LOCATION = "location";
    private SharedPreferences sharedPreferences;

    public LocalLocationManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void shareLocation(boolean share) {
        sharedPreferences.edit().putBoolean(LOCATION, share).apply();
    }

    @Override
    public boolean checkShareLocation() {
        return sharedPreferences.getBoolean(LOCATION, false);
    }
}
