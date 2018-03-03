package com.example.thienpro.mvp_firebase.ultils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by hung.nguyendk on 8/27/17.
 */

public class SHLocationManager {


    private static final String permissions[] = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public static boolean checkPermission(Context context) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static Location getLastKnowLocation(Context context) {
        if (checkPermission(context)) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            Location getLastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (getLastLocation != null) {
                return getLastLocation;
            }
            getLastLocation = locationManager.getLastKnownLocation
                    (LocationManager.GPS_PROVIDER);
            if (getLastLocation != null) {
                return getLastLocation;
            }
            getLastLocation = locationManager.getLastKnownLocation
                    (LocationManager.NETWORK_PROVIDER);
            if (getLastLocation != null) {
                return getLastLocation;
            }
            Location location = new Location(LocationManager.PASSIVE_PROVIDER);
            location.setLatitude(10.7993946);
            location.setLongitude(106.613679);
            return location;
        }
        return null;
    }
}
