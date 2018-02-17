package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityHomeBinding;
import com.example.thienpro.mvp_firebase.view.adapters.HomeFragmentPagerAdapter;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;

    public static void startActiviry(Context context){
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.tlHome.setupWithViewPager(binding.vpHome);
        binding.vpHome.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager()));
        binding.tlHome.getTabAt(0).setIcon(R.drawable.ic_home);
        binding.tlHome.getTabAt(1).setIcon(R.drawable.ic_profile);
        binding.tlHome.getTabAt(2).setIcon(R.drawable.ic_menu);
        binding.vpHome.setCurrentItem(1);
        binding.vpHome.setOffscreenPageLimit(3);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
