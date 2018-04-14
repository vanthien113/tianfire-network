package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.presenter.ListLocationPresenter;
import com.example.thienpro.mvp_firebase.view.ListLocationView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ListLocationPresenterImpl extends BasePresentermpl<ListLocationView> implements ListLocationPresenter {
    private LocationInteractor locationInteractor;

    public ListLocationPresenterImpl(LocationInteractor locationInteractor) {
        this.locationInteractor = locationInteractor;
    }

    @Override
    public void getListLocation() {
        getView().showLoadingDialog();
        locationInteractor.getListLocation(new LocationInteractor.GetListLocationCallback() {
            @Override
            public void listLocation(ArrayList<UserLocation> locations, DatabaseError e) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showDatabaseError(e);
                } else {
                    getView().showListLocation(locations);
                }
            }
        });
    }
}
