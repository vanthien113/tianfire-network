package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityShareLocationBinding;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.presenter.ShareLocationPresenter;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.ultils.SHLocationManager;
import com.example.thienpro.mvp_firebase.view.ShareLocationView;
import com.example.thienpro.mvp_firebase.view.adapters.ShareLocationAdapter;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;

import java.util.ArrayList;

public class ShareLocationActivity extends BaseActivity<ActivityShareLocationBinding> implements ShareLocationView, ShareLocationAdapter.ListLocationListener {
    private ShareLocationPresenter presenter;
    private static boolean checked = false;
    private ShareLocationAdapter adapter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ShareLocationActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_location;
    }

    @Override
    protected void init() {
        presenter = getAppComponent().getCommonComponent().getAppSettingPresenter();
        presenter.attachView(this);
        getBinding().setEvent(this);

        if (checked) {
            getBinding().cbLocation.setChecked(true);
        }

        presenter.getListLocation();

        getBinding().rvLocation.setLayoutManager(LayoutUltils.getLinearLayoutManager(this));
        getBinding().rvLocation.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        getBinding().tbShareLocation.getImageBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onCheckLocationClick() {
        if (getBinding().cbLocation.isChecked()) {
            checked = true;
            presenter.pushLocation(ShareLocationActivity.this);
        } else {
            checked = false;
            presenter.stopPushLocation();
        }
    }

    @Override
    public void showListLocation(ArrayList<UserLocation> listLocation) {
        adapter = new ShareLocationAdapter(listLocation, this);
        getBinding().rvLocation.setAdapter(adapter);
    }

    @Override
    public void showSharingMessage() {
        showToastMessage(R.string.dang_chia_se_vi_tri);
    }

    @Override
    public void showStopShareMessage() {
        showToastMessage(R.string.dung_chia_se_vi_tri);
    }

    @Override
    protected void startScreen() {

    }

    @Override
    protected void resumeScreen() {
        SHLocationManager.checkLocationEnable(this);

    }

    @Override
    protected void pauseScreen() {

    }

    @Override
    protected void destroyScreen() {

    }

    @Override
    public void showListLocation(UserLocation location) {
        UserLocationActivity.startActivity(this, location);

    }
}
