package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityLoginBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.LoginPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.LoginPresenter;
import com.example.thienpro.mvp_firebase.view.LoginView;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private ActivityLoginBinding mainBinding;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mainBinding.setEvent(this);
        loginPresenter = new LoginPresenterImpl(this, this);
        loginPresenter.signedInCheck();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        loginPresenter.signedInCheck();
//    }

    @Override
    public void navigationToHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent); // Chuyển màn đến màn hình Home. sử dụng context.startactivity
        finish();
    }

    @Override
    public void navigationToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginClick() {
        if (mainBinding.etEmail.getText().toString().equals(mainBinding.etPassword.getText().toString()))
            Toast.makeText(this, "Hãy nhập email và password!", Toast.LENGTH_SHORT).show();
        else
            loginPresenter.onSignIn(mainBinding.etEmail.getText().toString(), mainBinding.etPassword.getText().toString());
    }

    @Override
    public void onRegisterClick() {
        navigationToRegister();
    }

    @Override
    public void navigationToLogin(Context context) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigationToVerifiEmail(Context context) {
        Intent intent = new Intent(context, VerifiEmailActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFail(Context context) {
        Toast.makeText(context, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
    }
}
