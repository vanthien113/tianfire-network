package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityFriendProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.FriendProfilePresenter;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.FriendProfileView;
import com.example.thienpro.mvp_firebase.view.adapters.FriendProfileAdapter;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.adapters.ProfileAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

import java.util.ArrayList;

public class FriendProfileActivity extends BaseActivity<ActivityFriendProfileBinding> implements FriendProfileView, HomeAdapter.ListPostMenuListener, ProfileAdapter.ItemProfileClickListener {
    private static String USER_ID = "userId";

    private String userId;
    private FriendProfilePresenter presenter;
    private User user;
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

        presenter.getFriendInfomation(userId);

        getBinding().setEvent(this);

        viewDataBinding.tbProfile.getImageBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
    public void onChangeAvatar() {

    }

    @Override
    public void onChangeCover() {

    }

    @Override
    public void onShowListPictureClick() {
        PictureActivity.startActivity(this, userId);
    }

    @Override
    public void onPost() {

    }

    @Override
    public void onDeletePost(Post post) {

    }

    @Override
    public void onDownload(String imageUrl) {

    }

    @Override
    public void onFriendProfile(String userId) {

    }

    @Override
    public void onEditPost(Post post) {

    }

    @Override
    public void showListPost(ArrayList<Post> listPost) {
        adapter = new FriendProfileAdapter(listPost, user, this, this);

        getBinding().rvProfile.setLayoutManager(LayoutUltils.getLinearLayoutManager(this));
        getBinding().rvProfile.setAdapter(adapter);

    }

    @Override
    public void showUserInfomation(User user) {
        this.user = user;
        getBinding().tbProfile.setTvTitle(user.getName());
    }

    @Override
    public void getFriendPost() {
        presenter.getFriendPost(userId);
    }
}
