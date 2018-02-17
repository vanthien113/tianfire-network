package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityLoginBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.LoginPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.LoginPresenter;
import com.example.thienpro.mvp_firebase.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private ActivityLoginBinding binding;
    private LoginPresenter loginPresenter;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setEvent(this);
        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.signedInCheck();
    }

    @Override
    public void navigationToHome() {
        HomeActivity.startActiviry(this);
        finish();
    }

    @Override
    public void onLoginClick() {
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Hãy nhập email và password!", Toast.LENGTH_SHORT).show();
        } else
            loginPresenter.onSignIn(email, password);
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
    public void onLoginFail() {
        Toast.makeText(this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
    }
}
