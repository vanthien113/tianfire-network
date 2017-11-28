package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityVerifiemailBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.VerifiEmailPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.VerifiEmailPresenter;
import com.example.thienpro.mvp_firebase.view.VerifiEmailView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class VerifiEmailActivity extends AppCompatActivity implements VerifiEmailView {
    private ActivityVerifiemailBinding binding;
    private VerifiEmailPresenter verifiEmailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verifiemail);
        binding.setEvent(this);

        verifiEmailPresenter = new VerifiEmailPresenterImpl(this, this);
        verifiEmailPresenter.verifiEmail();
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
    public void navigationToHome(Context context) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCancelClick() {
        verifiEmailPresenter.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNextClick() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
