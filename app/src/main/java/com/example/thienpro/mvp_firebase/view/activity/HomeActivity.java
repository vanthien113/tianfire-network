package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.adapters.HomeFragmentPagerAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;
import com.example.thienpro.mvp_firebase.view.listener.HomeNavigationListener;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> implements HomeNavigationListener {
    public static void startActiviry(Context context) {
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

    @Override
    public void navigationToEditPostActivity(Post post) {
        EditPostActivity.start(this, post);
    }

    @Override
    public void navigationToFriendProfileActivity(String userId) {
        FriendProfileActivity.startActivity(this, userId);
    }

    @Override
    public void navigationToPictureActivity(String userId) {
        PictureActivity.startActivity(this, userId);
    }

    @Override
    public void navigationToPostActivity() {
        PostActivity.startActivity(this);
    }

    @Override
    public void navigationToChangePasswordActivity() {
        ChangePasswordActivity.startActivity(this);
    }

    @Override
    public void navigationToAppSettingActivity() {
        AppSettingActivity.startActivity(this);
    }

    @Override
    public void navigationToLoginActivity() {
        LoginActivity.startActivity(this);
        finish();
    }

    @Override
    public void navigationToEditInfoActivity() {
        EditInfoActivity.startActivity(this);
    }
}
