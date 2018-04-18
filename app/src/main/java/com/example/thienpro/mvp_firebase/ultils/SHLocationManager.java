package com.example.thienpro.mvp_firebase.ultils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

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

    public static void checkLocationEnable(final Activity activity) {
        LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Ask the user to enable GPS
            new AlertDialog.Builder(activity)
                    .setTitle("Vị trí")
                    .setMessage("Mở vị trí để sử dụng tính năng này!!!")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Launch settings, allowing user to make a change
                            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivity(i);
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //No location service, no Activity
                            activity.finish();
                        }
                    })
                    .create().show();
        }
    }

    public interface OnCurrentLocationCallback {
        void callback(Location location);
    }

    public static void getCurrentLocation(final Context context, final OnCurrentLocationCallback locationCallback) {
        FusedLocationProviderClient currentLocation = LocationServices.getFusedLocationProviderClient(context);

        if (checkPermission(context)) {
            currentLocation.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location == null) {
                        Location location1 = new Location(LocationManager.PASSIVE_PROVIDER);
                        location1.setLatitude(10.7993946);
                        location1.setLongitude(106.613679);
                        locationCallback.callback(location1);
                    } else
                        locationCallback.callback(location);
                }
            });
        }
    }

    public static String mapUrl(LatLng current, LatLng friend) {
        return "http://maps.google.com/maps?f=d&hl=en&saddr=" + current.latitude + "," + current.longitude + "&daddr=" + friend.latitude + "," + friend.longitude;
    }
}
