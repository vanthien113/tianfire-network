package com.example.thienpro.mvp_firebase.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.adapters.HomeFragmentPagerAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;
import com.example.thienpro.mvp_firebase.view.listener.HomeNavigationListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

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
        getBinding().tlHome.getTabAt(2).setIcon(R.drawable.ic_setting);
        getBinding().vpHome.setCurrentItem(1);
        getBinding().vpHome.setOffscreenPageLimit(3);

        requestPermission();
    }

    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // do you work now

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
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
        ShareLocationActivity.startActivity(this);
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

    @Override
    public void navigationToSearchActivity() {
        SearchUserActivity.startActivity(this);
    }
}
