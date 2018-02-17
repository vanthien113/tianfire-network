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
import com.example.thienpro.mvp_firebase.presenter.Impl.RegisterPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.RegistrerPresenter;
import com.example.thienpro.mvp_firebase.view.RegisterView;

/**
 * Created by ThienPro on 11/9/2017.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private ActivityRegisterBinding binding;
    private RegistrerPresenter presenter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setEvent(this);
        presenter = new RegisterPresenterImpl(this);

        name = getIntent().getStringExtra(NAME);
        address = getIntent().getStringExtra(ADDRESS);
        sex = getIntent().getBooleanExtra(SEX, false);
    }

    @Override
    public void navigationToVerifiEmail() {
        VerifiEmailActivity.startActivity(this);
    }

    @Override
    public void onNextClick() {
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        String repassword = binding.etRepassword.getText().toString();

        if (email.isEmpty() || password.isEmpty() || repassword.isEmpty())
            Toast.makeText(this, "Không được để trống các trường!", Toast.LENGTH_SHORT).show();
        else {
            if (password.length() >= 6) {
                if (password.equals(repassword)) {
                    presenter.register(email, password, name, address, sex);
                } else
                    Toast.makeText(this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Mật khẩu phải lớn hơn 6 ký tự!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRegisterFail(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackClick() {
        LoginActivity.startActivity(this);
        finish();
    }
}
