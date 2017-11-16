package com.example.thienpro.mvp_firebase.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityProfileBinding;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.presenter.impl.ProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class ProfileActivity extends AppCompatActivity implements ProfileView {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private Date today;
    private String day;
    private ProfilePresenter mProfilePresenter;
    private ActivityProfileBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
        binding.setEvent(this);
    }

    @Override
    public void onPost() {
        user = mAuth.getCurrentUser();

        today = new Date();
        today.getDate();
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        day = format.format(today);

        mProfilePresenter = new ProfilePresenterImpl(mDatabase);
        //Tao post
        mProfilePresenter.writeNewPost(user.getUid().toString(), day, binding.etPost.getText().toString());
    }
}
