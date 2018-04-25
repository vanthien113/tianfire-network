package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityVerifiemailBinding;
import com.example.thienpro.mvp_firebase.presenter.VerifiEmailPresenter;
import com.example.thienpro.mvp_firebase.view.VerifiEmailView;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class VerifiEmailActivity extends BaseActivity<ActivityVerifiemailBinding> implements VerifiEmailView {
    private VerifiEmailPresenter presenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, VerifiEmailActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verifiemail;
    }

    @Override
    protected void init() {
        viewDataBinding.setEvent(this);

        presenter = getAppComponent().getCommonComponent().getVerifiEmailPresenter();
        presenter.attachView(this);

        presenter.verifiEmail();
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

    @Override
    public void sendverifiEmailComplete(String email) {
        getBinding().tvEmail.setText(email);
        getBinding().tvEmail.setTextColor(Color.BLUE);
    }

    @Override
    public void sendverifiEmailFail(String email) {
        getBinding().tvEmail.setText(email);
        getBinding().tvEmail.setTextColor(Color.RED);
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
