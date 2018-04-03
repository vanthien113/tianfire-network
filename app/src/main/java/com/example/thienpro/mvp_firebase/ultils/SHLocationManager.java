package com.example.thienpro.mvp_firebase.ultils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by hung.nguyendk on 8/27/17.
 */

public class SHLocationManager {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static OnCurrentLocationCallback callback;

    private static boolean checkPermission(final Context context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("Quyền truy cập vị trí")
                        .setMessage("Hãy cấp quyền truy cập vị trí")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
                LogUltil.log(SHLocationManager.class, "CHECKED");

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults, Context context) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    LogUltil.log(SHLocationManager.class, "Request Per");

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //Request location updates:
//                        locationManager.requestLocationUpdates(provider, 400, 1, this);

//                        getCurrentLocation(context, callback);
                        getCurrentLocation(context, callback);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    getCurrentLocation(context, callback);
                }
                return;
            }
        }
    }

    public interface OnCurrentLocationCallback {
        void callback(Location location);
    }

    public static void getCurrentLocation(final Context context, final OnCurrentLocationCallback locationCallback) {
        callback = locationCallback;
        FusedLocationProviderClient currentLocation = LocationServices.getFusedLocationProviderClient(context);

        if (checkPermission(context)) {
            currentLocation.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    callback.callback(location);

                    Toast.makeText(context, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


//    public static Location getLastKnowLocation(Context context, Activity activity) {
//        if (checkPermission(context)) {
//            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//
//            Location getLastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//
//            if (getLastLocation != null) {
//                return getLastLocation;
//            }
//            getLastLocation = locationManager.getLastKnownLocation
//                    (LocationManager.GPS_PROVIDER);
//            if (getLastLocation != null) {
//                return getLastLocation;
//            }
//            getLastLocation = locationManager.getLastKnownLocation
//                    (LocationManager.NETWORK_PROVIDER);
//            if (getLastLocation != null) {
//                return getLastLocation;
//            }
//            Location location = new Location(LocationManager.PASSIVE_PROVIDER);
//            location.setLatitude(10.7993946);
//            location.setLongitude(106.613679);
//            return location;
//        }
//        return null;
//    }

    public static String mapUrl(LatLng current, LatLng friend) {
        return "http://maps.google.com/maps?f=d&hl=en&saddr=" + current.latitude + "," + current.longitude + "&daddr=" + friend.latitude + "," + friend.longitude;
    }
}
