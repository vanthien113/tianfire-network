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
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {
    public static void startActiviry(Context context){
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void init() {
        getBinding().tlHome.setupWithViewPager(viewDataBinding.vpHome);
        getBinding().vpHome.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager()));
        getBinding().tlHome.getTabAt(0).setIcon(R.drawable.ic_home);
        getBinding().tlHome.getTabAt(1).setIcon(R.drawable.ic_profile);
        getBinding().tlHome.getTabAt(2).setIcon(R.drawable.ic_menu);
        getBinding().vpHome.setCurrentItem(1);
        getBinding().vpHome.setOffscreenPageLimit(3);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
