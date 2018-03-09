package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityFriendProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.FriendProfilePresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.FriendProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.view.FriendProfileView;
import com.example.thienpro.mvp_firebase.view.adapters.FriendProfileAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

import java.util.ArrayList;

public class FriendProfileActivity extends BaseActivity<ActivityFriendProfileBinding> implements FriendProfileView {
    private String userId;
    private FriendProfilePresenter presenter;
    private FriendProfileAdapter adapter;

    public static void startActivity(Context context, String userId) {
        Intent intent = new Intent(context, FriendProfileActivity.class);

        intent.putExtra("userId", userId);

        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_profile;
    }

    @Override
    protected void init() {
        userId = getIntent().getStringExtra("userId");

        presenter = new FriendProfilePresenterImpl(this);
        presenter.attachView(this);

        presenter.getFriendPost(userId);
        presenter.getFriendInfomation(userId);

        viewDataBinding.setEvent(this);

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
    public void onShowListPictureClick() {
        PictureActivity.startActivity(this, userId);
    }

    @Override
    public void showListPost(ArrayList<Post> listPost) {
        adapter = new FriendProfileAdapter(listPost);

        viewDataBinding.rvProfile.setLayoutManager(new LinearLayoutManager(viewDataBinding.getRoot().getContext(), OrientationHelper.VERTICAL, false));
        viewDataBinding.rvProfile.setAdapter(adapter);
    }

    @Override
    public void showUserInfomation(User user) {
        viewDataBinding.setData(user);

        Glide.with(this)
                .load(user.getCover())
                .asBitmap().centerCrop()
                .into(viewDataBinding.ivCover);

        Glide.with(this)
                .load(user.getAvatar())
                .asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(viewDataBinding.ivAvatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        viewDataBinding.ivAvatar.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }
}
