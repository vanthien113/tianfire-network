package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityUserLocationBinding;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.presenter.Impl.UserLocationPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.UserLocationPresenter;
import com.example.thienpro.mvp_firebase.view.UserLocationView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserLocationActivity extends BaseActivity<ActivityUserLocationBinding> implements OnMapReadyCallback, UserLocationView {
    private GoogleMap map;
    private UserLocation location;
    private UserLocationPresenter presenter;

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
        presenter = new UserLocationPresenterImpl(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getIntent() != null) {
            location = (UserLocation) getIntent().getSerializableExtra("location");

            presenter.getUserLocation(location.getUserId());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    public void showUserLocation(UserLocation location) {
        if (map != null) {
            map.clear();

            LatLng currentLatLng = new LatLng(location.getLat(), location.getLng());

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12.0f));
            map.addMarker(new MarkerOptions().position(currentLatLng).title(location.getUserName()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.stopGetUserLocation();
    }

    @Override
    protected void startScreen() {

    }

    @Override
    protected void resumeScreen() {

    }

    @Override
    protected void pauseScreen() {

    }

    @Override
    protected void destroyScreen() {

    }

}
