package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.LocationInteractorImpl;
import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.presenter.ListLocationPresenter;
import com.example.thienpro.mvp_firebase.view.ListLocationView;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ListLocationPresenterImpl implements ListLocationPresenter {
    private LocationInteractor interactor;
    private ListLocationView view;

    public ListLocationPresenterImpl(ListLocationView view, Context context) {
        this.interactor = new LocationInteractorImpl(context);
        this.view = view;
    }

    @Override
    public void getListLocation() {
        view.showLoadingDialog();
        interactor.getListLocation(new LocationInteractor.GetListLocationCallback() {
            @Override
            public void listLocation(ArrayList<UserLocation> locations, DatabaseError e) {
                view.hideLoadingDialog();
                if (e != null) {

                } else {
                    view.showListLocation(locations);
                }
            }
        });
    }
}
