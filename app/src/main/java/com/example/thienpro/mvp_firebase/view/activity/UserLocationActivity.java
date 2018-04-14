package com.example.thienpro.mvp_firebase.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityUserLocationBinding;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.presenter.Impl.UserLocationPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.UserLocationPresenter;
import com.example.thienpro.mvp_firebase.ultils.SHLocationManager;
import com.example.thienpro.mvp_firebase.view.UserLocationView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserLocationActivity extends BaseActivity<ActivityUserLocationBinding> implements OnMapReadyCallback, UserLocationView {
    private GoogleMap map;
    private UserLocation location;
    private UserLocationPresenter presenter;
    private LatLng friendtLatLng;

    public static void startActivity(Context context, UserLocation location) {
        Intent intent = new Intent(context, UserLocationActivity.class);
        intent.putExtra("location", location);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_location;
    }

    @Override
    protected void init() {
        presenter = getAppComponent().getCommonComponent().getUserLocationPresenter();
        presenter.attachView(this);

        getBinding().setEvent(this);
        initMap();

        if (getIntent() != null) {
            location = (UserLocation) getIntent().getSerializableExtra("location");

            presenter.getUserLocation(location.getUserId());
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
    }

    @Override
    public void showUserLocation(UserLocation location) {
        if (map != null) {
            map.clear();
            friendtLatLng = new LatLng(location.getLat(), location.getLng());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(friendtLatLng, 15.0f));
            map.addMarker(new MarkerOptions().position(friendtLatLng).title(location.getUserName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
        }
    }

    @Override
    public void onSendToMapClick() {
        SHLocationManager.getCurrentLocation(this, new SHLocationManager.OnCurrentLocationCallback() {
            @Override
            public void callback(Location location) {
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(SHLocationManager.mapUrl(currentLatLng, friendtLatLng)));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void startScreen() {

    }

    @Override
    protected void resumeScreen() {

    }

    @Override
    protected void pauseScreen() {
        presenter.stopGetUserLocation();
    }

    @Override
    protected void destroyScreen() {

    }

}
