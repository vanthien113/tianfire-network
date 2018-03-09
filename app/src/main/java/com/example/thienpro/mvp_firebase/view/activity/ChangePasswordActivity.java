package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityChangePasswordBinding;
import com.example.thienpro.mvp_firebase.presenter.ChangePasswordPresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.ChangePasswordImpl;
import com.example.thienpro.mvp_firebase.view.ChangePasswordView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

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
        presenter = new ChangePasswordImpl(this);
        presenter.attachView(this);

        viewDataBinding.setEvent(this);
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
    public void onChangePasswordClick() {
        String password = viewDataBinding.etPassword.getText().toString();
        String rePassword = viewDataBinding.etRePassword.getText().toString();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            showToastMessage("Nhập mật khẩu!!!");
        } else {
            if (TextUtils.equals(password, rePassword)) {
                if (password.length() >= 6) {
                    presenter.changePassword(password);
                } else {
                    showToastMessage("Mật khẩu có độ dài lớn hơn 6 kí tự");
                }
            } else {
                showToastMessage("Mật khẩu không trùng khớp");
            }
        }
    }
}
