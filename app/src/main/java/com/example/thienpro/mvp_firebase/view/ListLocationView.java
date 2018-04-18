package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.bases.BaseView;

import java.util.ArrayList;

public interface ListLocationView extends BaseView{
    void showListLocation(ArrayList<UserLocation> listLocation);
}
