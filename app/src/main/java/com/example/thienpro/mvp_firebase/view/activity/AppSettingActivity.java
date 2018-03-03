package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityAppSettingBinding;
import com.example.thienpro.mvp_firebase.presenter.AppSettingPresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.AppSettingPresenterImpl;
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
        presenter = new AppSettingPresenterImpl(this, this);
        viewDataBinding.setEvent(this);
    }

    @Override
    public void onCheckLocationClick() {
        if (viewDataBinding.cbLocation.isChecked()) {
            presenter.pushLocation(true);
        } else {
            presenter.pushLocation(false);
        }
    }

    @Override
    public void showError(Exception e) {
        showToastMessage(e.getMessage());
    }

    @Override
    public void onShowListLocationClick() {
        ListLocationActivity.startActivity(this);
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
