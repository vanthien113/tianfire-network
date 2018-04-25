package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.bases.BaseView;

import java.util.ArrayList;

public interface ShareLocationView extends BaseView {
    void onCheckLocationClick();

    void showListLocation(ArrayList<UserLocation> listLocation);

    void showSharingMessage();

    void showStopShareMessage();
}
