package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityRegisterBinding;
import com.example.thienpro.mvp_firebase.presenter.RegisterPresenter;
import com.example.thienpro.mvp_firebase.presenter.impl.RegisterPresenterImpl;
import com.example.thienpro.mvp_firebase.view.RegisterView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ThienPro on 11/9/2017.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ActivityRegisterBinding binding;
    private RegisterPresenter registerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        registerPresenter = new RegisterPresenterImpl(mDatabase);
        binding.setEvent(this);
    }

    @Override
    public void onRegisterClick() {
        if (binding.etRegisterpw.getText().toString().equals(binding.etRegisterrepw.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(binding.etRegisterun.getText().toString(), binding.etRegisterpw.getText().toString())
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                registerPresenter.writeNewUser(user.getUid().toString(), binding.etRegisterun.getText().toString(),
                                        binding.etRegistername.getText().toString(), binding.etRegisteradd.getText().toString(),
                                        binding.rbNam.isChecked());
                                navigationToHome();
                            } else {
                                onRegisterFail();
                            }

                        }
                    });
        }
        else onRegisterFail();
    }

    @Override
    public void navigationToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterFail() {
        Toast.makeText(RegisterActivity.this, "Đăng kí không thành công!", Toast.LENGTH_SHORT).show();
    }
}
