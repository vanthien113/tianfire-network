package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;
import com.example.thienpro.mvp_firebase.databinding.ActivityChangePasswordBinding;
import com.example.thienpro.mvp_firebase.presenter.ChangePasswordPresenter;
import com.example.thienpro.mvp_firebase.view.ChangePasswordView;

public class ChangePasswordActivity extends BaseActivity<ActivityChangePasswordBinding> implements ChangePasswordView {
    private ChangePasswordPresenter presenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ChangePasswordActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void init() {
        presenter = getAppComponent().getCommonComponent().getChangePasswordPresenter();
        presenter.attachView(this);
        getBinding().setEvent(this);
    }

    @Override
    public void onChangePasswordClick() {
        String password = getBinding().etPassword.getText().toString();
        String rePassword = getBinding().etRePassword.getText().toString();

        if (validate(password, rePassword)) {
            presenter.changePassword(password);
        }
    }

    @Override
    public void showChangePasswordComplete() {
        showToastMessage(R.string.thay_doi_mat_khau_thanh_cong);
    }

    private boolean validate(String password, String rePassword) {
        if (TextUtils.isEmpty(password)) {
            showToastMessage(R.string.vui_long_nhap_password);
            return false;
        } else if (TextUtils.isEmpty(rePassword)) {
            showToastMessage(R.string.vui_long_nhap_repassword);
            return false;
        } else if (!TextUtils.equals(password, rePassword)) {
            showToastMessage(R.string.mat_khau_khong_trung_khop);
            return false;
        } else if (password.length() < 6) {
            showToastMessage(R.string.mat_khau_phai_dai_hon_6_ki_tu);
            return false;
        }
        return true;
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
        presenter.detach();
    }

}
