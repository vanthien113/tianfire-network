package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.thienpro.mvp_firebase.view.fragment.HomeFragment;
import com.example.thienpro.mvp_firebase.view.fragment.ProfileFragment;
import com.example.thienpro.mvp_firebase.view.fragment.SettingFragment;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final int FRAGMENT_COUNT = 3;
    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return ProfileFragment.newInstance();
            default:
                return SettingFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }
}
