package com.example.thienpro.mvp_firebase.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityRegisterBinding;
import com.example.thienpro.mvp_firebase.eventhandler.ActivityRegisterEH;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ThienPro on 11/9/2017.
 */

public class RegisterActivity extends AppCompatActivity implements ActivityRegisterEH {
    private FirebaseAuth mAuth;
    private ActivityRegisterBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
    }

    public void updateUI(FirebaseUser user) {
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onRegisterClick() {
        mAuth.createUserWithEmailAndPassword(binding.etRegisterun.getText().toString(), binding.etRegisterpw.getText().toString())
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }
}
