package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityFriendProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.FriendProfilePresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.FriendProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.FriendProfileView;
import com.example.thienpro.mvp_firebase.view.adapters.FriendProfileAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

import java.util.ArrayList;

public class FriendProfileActivity extends BaseActivity<ActivityFriendProfileBinding> implements FriendProfileView {
    private static String USER_ID = "userId";

    private String userId;
    private FriendProfilePresenter presenter;
    private FriendProfileAdapter adapter;

    public static void startActivity(Context context, String userId) {
        Intent intent = new Intent(context, FriendProfileActivity.class);

        intent.putExtra(USER_ID, userId);

        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_profile;
    }

    @Override
    protected void init() {
        userId = getIntent().getStringExtra(USER_ID);

        presenter = getAppComponent().getCommonComponent().getFriendProfilePresenter();
        presenter.attachView(this);

        presenter.getFriendPost(userId);
        presenter.getFriendInfomation(userId);

        getBinding().setEvent(this);

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

        getBinding().rvProfile.setLayoutManager(new LinearLayoutManager(getBinding().getRoot().getContext(), OrientationHelper.VERTICAL, false));
        getBinding().rvProfile.setAdapter(adapter);
    }

    @Override
    public void showUserInfomation(User user) {
        viewDataBinding.setData(user);

        SHBitmapHelper.bindImage(getBinding().ivCover, user.getCover());
        SHBitmapHelper.bindCircularImage(getBinding().ivAvatar, user.getAvatar());
    }
}
