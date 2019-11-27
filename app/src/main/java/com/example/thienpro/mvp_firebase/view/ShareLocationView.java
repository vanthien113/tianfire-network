package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.bases.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface ShareLocationView extends BaseView {
    void onCheckLocationClick();

    void showListLocation(List<UserLocation> listLocation);

    void showSharingMessage();

    void showStopShareMessage();
}
