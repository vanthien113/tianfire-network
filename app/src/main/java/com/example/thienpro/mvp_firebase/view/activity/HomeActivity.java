package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements HomeView {
    private ActivityHomeBinding binding;
    private DatabaseReference mDatabase;
    private ArrayList<Post> listPost;
    private HomeAdapter homeAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setEvent(this);

        setSupportActionBar(binding.tbHome);
        listPost = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvHome.setLayoutManager(linearLayoutManager);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                binding.tvLoading.setVisibility(View.GONE);
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 2000);
        //TODO nice idea about 2s loading, but not implement good.
        mDatabase = FirebaseDatabase.getInstance().getReference();
        binding.setEvent(this);
        homeAdapter = new HomeAdapter(listPost);
        binding.rvHome.setAdapter(homeAdapter);
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
                    String secondValue = (String) map.get("name");
                    String thirdValue = (String) map.get("timePost");
                    String foureValue = (String) map.get("post");

                    Post post = new Post(firstValue, secondValue, thirdValue, foureValue);
                    listPost.add(post);
                }
                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listPost.clear();
        ShowList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public void onSignOutClick() {
        FirebaseAuth.getInstance().signOut();
        navigationToMain();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_profile:
                navigationToProfile();
                return true;
            case R.id.it_edit:
                navigationToEditInfo();
                return true;
            case R.id.it_logout:
                onSignOutClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void navigationToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigationToEditInfo() {
        Intent intent = new Intent(this, EditInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigationToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
