package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityRegisterBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.RegisterPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.RegistrerPresenter;
import com.example.thienpro.mvp_firebase.view.RegisterView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

/**
 * Created by ThienPro on 11/9/2017.
 */

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> implements RegisterView {
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
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        viewDataBinding.setEvent(this);
        presenter = new RegisterPresenterImpl(this);
        presenter.attachView(this);

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
        String email = viewDataBinding.etEmail.getText().toString();
        String password = viewDataBinding.etPassword.getText().toString();
        String repassword = viewDataBinding.etRepassword.getText().toString();

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
