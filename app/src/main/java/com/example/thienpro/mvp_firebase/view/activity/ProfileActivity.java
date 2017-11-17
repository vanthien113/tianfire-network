package com.example.thienpro.mvp_firebase.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.presenter.impl.ProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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
    private ArrayList<Post> listPost;
    private HomeAdapter homeAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SimpleDateFormat format;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mProfilePresenter = new ProfilePresenterImpl(mDatabase);
        user = mAuth.getCurrentUser();
        listPost = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvProfile.setLayoutManager(mLinearLayoutManager);

        homeAdapter = new HomeAdapter(listPost);
        binding.rvProfile.setAdapter(homeAdapter);
        binding.setEvent(this);

        ShowList();
    }

    public void ShowList() {
        mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String firstValue = (String) map.get("id");
                    String secondValue = (String) map.get("post");
                    String thirdValue = (String) map.get("timePost");

                    Post post = new Post(firstValue, thirdValue, secondValue);
                    listPost.add(post);
                }
                homeAdapter = new HomeAdapter(listPost);
                binding.rvProfile.setAdapter(homeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void onPost() {
        today = new Date();
        today.getDate();
        format = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        day = format.format(today);

        //Tao post
        mProfilePresenter.writeNewPost(user.getUid().toString(), day, binding.etPost.getText().toString());
        ShowList();
    }
}
