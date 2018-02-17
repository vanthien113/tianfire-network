package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityVerifiemailBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.VerifiEmailPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.VerifiEmailPresenter;
import com.example.thienpro.mvp_firebase.view.VerifiEmailView;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class VerifiEmailActivity extends AppCompatActivity implements VerifiEmailView {
    private ActivityVerifiemailBinding binding;
    private VerifiEmailPresenter presenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, VerifiEmailActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verifiemail);
        binding.setEvent(this);

        presenter = new VerifiEmailPresenterImpl(this, this);
        presenter.verifiEmail();
    }

    @Override
    public void sendverifiEmailComplete(String email) {
        binding.tvEmail.setText(email);
        binding.tvEmail.setTextColor(Color.BLUE);
    }

    @Override
    public void sendverifiEmailFail(String email) {
        binding.tvEmail.setText(email);
        binding.tvEmail.setTextColor(Color.RED);
    }

    @Override
    public void navigationToHome() {
        HomeActivity.startActiviry(this);
        finish();
    }

    @Override
    public void navigationToLogin() {
        LoginActivity.startActivity(this);
    }

    @Override
    public void onCancelClick() {
        presenter.logOut();
        finish();
    }

    @Override
    public void onNextClick() {
        LoginActivity.startActivity(this);
        finish();
    }
}
