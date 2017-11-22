package com.example.thienpro.mvp_firebase.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityHomeBinding;
import com.example.thienpro.mvp_firebase.view.adapters.HomeFragmentPagerAdapter;
import com.example.thienpro.mvp_firebase.view.fragment.HomeFragment;
import com.example.thienpro.mvp_firebase.view.fragment.ProfileFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ArrayList<Fragment> mListFragments;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        mListFragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment().newInstance("Home");
        mListFragments.add(homeFragment);
        ProfileFragment profileFragment = new ProfileFragment().newInstance("Profile");
        mListFragments.add(profileFragment);

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        HomeFragmentPagerAdapter homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(manager, mListFragments);
        binding.vpHome.setAdapter(homeFragmentPagerAdapter);
        binding.tlHome.setupWithViewPager(binding.vpHome);
        binding.vpHome.setCurrentItem(0);
        binding.vpHome.setOffscreenPageLimit(2);
    }
}
