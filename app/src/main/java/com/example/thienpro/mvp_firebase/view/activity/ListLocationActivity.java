package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityListLocationBinding;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.presenter.Impl.ListLocationPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.ListLocationPresenter;
import com.example.thienpro.mvp_firebase.view.ListLocationView;
import com.example.thienpro.mvp_firebase.view.adapters.ListLocationAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

import java.util.ArrayList;

public class ListLocationActivity extends BaseActivity<ActivityListLocationBinding> implements ListLocationView, ListLocationAdapter.ListLocationListener {
    private LinearLayoutManager layoutManager;
    private ListLocationAdapter adapter;
    private ListLocationPresenter presenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ListLocationActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_location;
    }

    @Override
    protected void init() {
        presenter = new ListLocationPresenterImpl(this);
        presenter.attachView(this);

        layoutManager = new LinearLayoutManager(viewDataBinding.getRoot().getContext(), OrientationHelper.VERTICAL, false);
        getBinding().rvListLocation.setLayoutManager(layoutManager);
        getBinding().rvListLocation.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        presenter.getListLocation();
    }

    @Override
    public void showListLocation(ArrayList<UserLocation> listLocation) {
        adapter = new ListLocationAdapter(listLocation, this);
        getBinding().rvListLocation.setAdapter(adapter);

        if (listLocation.size() == 0) {
            getBinding().tvMessenger.setVisibility(View.VISIBLE);
        } else {
            getBinding().tvMessenger.setVisibility(View.GONE);
        }
    }

    @Override
    public void showListLocation(UserLocation location) {
        UserLocationActivity.startActivity(this, location);
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
