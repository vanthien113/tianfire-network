package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityRegisterBinding;
import com.example.thienpro.mvp_firebase.presenter.RegisterPresenterImpl;
import com.example.thienpro.mvp_firebase.view.RegisterView;

/**
 * Created by ThienPro on 11/9/2017.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private ActivityRegisterBinding binding;
    private RegisterPresenterImpl presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setEvent(this);
        presenter = new RegisterPresenterImpl(this, this);
    }

    @Override
    public void onRegisterClick() {
        presenter.register(binding.etEmail.getText().toString(), binding.etPassword.getText().toString(), binding.etRepassword.getText().toString(), binding.etName.getText().toString(), binding.etAddress.getText().toString(), binding.rbNam.isChecked());
    }

    public void navigationToHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public void onRegisterNull(Context context) {
        Toast.makeText(this, "Không được để trống các trường!", Toast.LENGTH_SHORT).show();
    }

    public void onRegisterEmailFail(Context context) {
        Toast.makeText(context, "Địa chỉ email không dúng!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRePasswordFail(Context context) {
        Toast.makeText(context, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
    }
}
