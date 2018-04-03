package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityLoginBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.LoginPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.LoginPresenter;
import com.example.thienpro.mvp_firebase.view.LoginView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginView {
    private LoginPresenter presenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        viewDataBinding.setEvent(this);

        presenter = new LoginPresenterImpl(this);
        presenter.attachView(this);

        presenter.signedInCheck();

        getBinding().etEmail.setText("vanthien113@gmail.com");
        getBinding().etPassword.setText("123456");
    }

    @Override
    public void navigationToHome() {
        HomeActivity.startActiviry(this);
        finish();
    }

    @Override
    public void onLoginClick() {
        String email = getBinding().etEmail.getText().toString();
        String password = getBinding().etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.hay_nhap_email_va_password, Toast.LENGTH_SHORT).show();
        } else
            presenter.onSignIn(email, password);
    }

    @Override
    public void onRegisterClick() {
        RegisterDetailActivity.startActivity(this);
    }

    @Override
    public void navigationToLogin() {
        LoginActivity.startActivity(this);
        finish();
    }

    @Override
    public void navigationToVerifiEmail() {
        VerifiEmailActivity.startActivity(this);
        finish();
    }

    @Override
    public void onShowPasswordClick() {
        if (getBinding().cbShowPassword.isChecked()) {
            getBinding().etPassword.setTransformationMethod(null);
        } else {
            getBinding().etPassword.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    @Override
    public void onForgotPasswordClick() {
        String email = viewDataBinding.etEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            showToastMessage("Hãy nhập email và nhấn vào nút Quên mật khẩu");
        } else {
            presenter.forgotPassword(getBinding().etEmail.getText().toString());
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
