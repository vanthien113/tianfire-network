package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.UserLocation;

import java.util.ArrayList;

public interface ListLocationView {
    void showListLocation(ArrayList<UserLocation> listLocation);

    void showLoadingDialog();

    void hideLoadingDialog();
}
