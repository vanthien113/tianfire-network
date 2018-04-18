package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityRegisterBinding;
import com.example.thienpro.mvp_firebase.presenter.RegisterPresenter;
import com.example.thienpro.mvp_firebase.view.RegisterView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

/**
 * Created by ThienPro on 11/9/2017.
 */

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements RegisterView {
    private RegisterPresenter presenter;

    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String SEX = "sex";

    private String name;
    private String address;
    private boolean sex;

    public static void startActivity(Context context, String name, String address, boolean sex) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(NAME, name);
        intent.putExtra(ADDRESS, address);
        intent.putExtra(SEX, sex);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        getBinding().setEvent(this);
        presenter = getAppComponent().getCommonComponent().getRegistrerPresenter();
        presenter.attachView(this);

        name = getIntent().getStringExtra(NAME);
        address = getIntent().getStringExtra(ADDRESS);
        sex = getIntent().getBooleanExtra(SEX, false);

        getBinding().tbRegister.getImageBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void navigationToVerifiEmail() {
        VerifiEmailActivity.startActivity(this);
    }

    @Override
    public void onNextClick() {
        String email = getBinding().etEmail.getText().toString();
        String password = getBinding().etPassword.getText().toString();
        String repassword = getBinding().etRepassword.getText().toString();

        if (validate(email, password, repassword)) {
            presenter.register(email, password, name, address, sex);
        }
    }

    private boolean validate(String email, String password, String rePassword) {
        if (TextUtils.isEmpty(email)) {
            showToastMessage(R.string.vui_long_nhap_email);
            return false;
        } else if (TextUtils.isEmpty(password)) {
            showToastMessage(R.string.vui_long_nhap_password);
            return false;
        } else if (TextUtils.isEmpty(rePassword)) {
            showToastMessage(R.string.vui_long_nhap_repassword);
            return false;
        } else if (password.length() < 6) {
            showToastMessage(R.string.mat_khau_phai_dai_hon_6_ki_tu);
            return false;
        } else if (!TextUtils.equals(password, rePassword)) {
            showToastMessage(R.string.mat_khau_khong_trung_khop);
            return false;
        }
        return true;
    }

    @Override
    public void onBackClick() {
        LoginActivity.startActivity(this);
        finish();
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
