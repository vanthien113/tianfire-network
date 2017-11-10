package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityHomeBinding;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements HomeView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setEvent(this);
    }

    @Override
    public void onSignOutClick() {
        FirebaseAuth.getInstance().signOut();
        navigationToMain();
    }

    @Override
    public void navigationToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
