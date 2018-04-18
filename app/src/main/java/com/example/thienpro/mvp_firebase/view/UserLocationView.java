package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.bases.BaseView;

public interface UserLocationView extends BaseView{
    void showUserLocation(UserLocation location);

    void onSendToMapClick();
}
