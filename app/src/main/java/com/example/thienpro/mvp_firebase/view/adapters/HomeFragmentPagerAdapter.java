package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> mListFragments;

    public HomeFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        mListFragments = new ArrayList<>();
        mListFragments = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragments.get(position);
    }

    @Override
    public int getCount() {
        return mListFragments.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title = mListFragments.get(position).getArguments().getString("msg");
//        return title;
//    }
}
