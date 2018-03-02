package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityAppSettingBinding;
import com.example.thienpro.mvp_firebase.presenter.AppSettingPresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.AppSettingPresenterImpl;
import com.example.thienpro.mvp_firebase.view.AppSettingView;

public class AppSettingActivity extends AppCompatActivity implements AppSettingView {
    private ActivityAppSettingBinding binding;
    private AppSettingPresenter presenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AppSettingActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_app_setting);

        presenter = new AppSettingPresenterImpl(this, this);
        binding.setEvent(this);
    }

    @Override
    public void onCheckLocationClick() {
        if (binding.cbLocation.isChecked()) {
            presenter.pushLocation(true);
        } else {
            presenter.pushLocation(false);
        }
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
