package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityAppSettingBinding;
import com.example.thienpro.mvp_firebase.presenter.AppSettingPresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.AppSettingPresenterImpl;
import com.example.thienpro.mvp_firebase.ultils.SHLocationManager;
import com.example.thienpro.mvp_firebase.view.AppSettingView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

public class AppSettingActivity extends BaseActivity<ActivityAppSettingBinding> implements AppSettingView {
    private AppSettingPresenter presenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AppSettingActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_setting;
    }

    @Override
    protected void init() {
        presenter = new AppSettingPresenterImpl(this);
        presenter.attachView(this);
        getBinding().setEvent(this);
        presenter.checkShareLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SHLocationManager.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onCheckLocationClick() {
        if (getBinding().cbLocation.isChecked()) {
            presenter.saveShareLocation(true);
            presenter.pushLocation(true);
        } else {
            presenter.saveShareLocation(false);
            presenter.pushLocation(false);
        }
    }

    @Override
    public void onShowListLocationClick() {
        ListLocationActivity.startActivity(this);
    }

    @Override
    public void shareLocation(boolean isShared) {
        getBinding().cbLocation.setChecked(isShared);

        if (isShared) {
            presenter.pushLocation(isShared);
        }
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
