package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.bases.BaseFragment;
import com.example.thienpro.mvp_firebase.databinding.FragmentProfileBinding;
import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.ultils.DownloadUltil;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.adapters.ProfileAdapter;
import com.example.thienpro.mvp_firebase.view.listener.HomeNavigationListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> implements ProfileView, HomeAdapter.ListPostMenuListener, ProfileAdapter.ItemProfileClickListener {
    public static final int REQUEST_CHANGE_AVATAR = 1;
    public static final int REQUEST_CHANGE_COVER = 2;

    private ProfilePresenter presenter;
    private HomeNavigationListener navigationListener;
    private ProfileAdapter adapter;
    private UserManager userManager;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void init(@Nullable View view) {
        presenter = getAppComponent().getCommonComponent().getProfilePresenter();
        presenter.attachView(this);

        userManager = getAppComponent().getUserManager();

        getBinding().rvProfile.setLayoutManager(LayoutUltils.getLinearLayoutManager(getContext()));
        adapter = new ProfileAdapter(userManager.getUser(), this, this);
        getBinding().rvProfile.setAdapter(adapter);

        presenter.loadPost();
        presenter.getAllUser();

        getBinding().srlProfile.setOnRefreshListener(() -> presenter.loadPost());

        getBinding().setEvent(this);

        getBinding().rvProfile.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(0);
                if (holder != null && holder.itemView != null) {
                    float fy = holder.itemView.getY();
                    getBinding().etSearch.setTranslationY(fy / 3);

                }
            }
        });

        getBinding().etSearch.setOnFocusChangeListener((view1, focused) -> {
            if (focused) {
                view1.setTranslationY(0f);
            }
        });
    }

    @Override
    protected void attach(Context context) {
        if (context instanceof HomeNavigationListener) {
            navigationListener = (HomeNavigationListener) context;
        }
    }

    @Override
    public void onPost() {
        navigationListener.navigationToPostActivity();
    }

    @Override
    public void showListPost(List<Post> listPost) {
        Collections.reverse(listPost);
        adapter.updateAdapter(listPost, userManager.getUser());
    }

    @Override
    public void onChangeAvatar() {
        SHBitmapHelper.takePhoto(this, REQUEST_CHANGE_AVATAR);
    }

    @Override
    public void onChangeCover() {
        SHBitmapHelper.takePhoto(this, REQUEST_CHANGE_COVER);
    }

    @Override
    public void onShowListPictureClick() {
        navigationListener.navigationToPictureActivity(userManager.getUser().getId());
    }

    @Override
    public void showLoading() {
        getBinding().srlProfile.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        getBinding().srlProfile.setRefreshing(false);
    }

    @Override
    public void showChangeComplete() {
        showToastMessage(R.string.cap_nhat_thanh_cong);
    }

    @Override
    public void showDeleteComplete() {
        showToastMessage(R.string.da_xoa);
    }

    @Override
    public void onUserUpdated() {
        presenter.loadPost();
    }

    @Override
    public void onSearchClick() {
        navigationListener.navigationToSearchActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(getContext(), requestCode, resultCode, data);
    }

    @Override
    public void onEditPost(Post post) {
        navigationListener.navigationToEditPostActivity(post);
    }

    @Override
    public void onAvatarClick(String avatar) {
        navigationListener.navigationToImageZoomActivity(avatar);
    }

    @Override
    public void onCoverClick(String cover) {
        navigationListener.navigationToImageZoomActivity(cover);
    }

    @Override
    public void onDeletePost(Post post) {
        presenter.deletePost(post);
    }

    @Override
    public void onDownload(String imageUrl) {
        DownloadUltil.startDownload(getContext(), imageUrl);
    }

    @Override
    public void onFriendProfile(String userId) {
        navigationListener.navigationToFriendProfileActivity(userId);
    }

    @Override
    public void onImageClick(String imageUrl) {
        navigationListener.navigationToImageZoomActivity(imageUrl);
    }

    @Override
    public void onCommentClick(Post post) {
        navigationListener.navigationToCommentActicity(post);
    }

    @Override
    protected void screenResume() {
    }

    @Override
    protected void screenPause() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    protected void screenStart(@Nullable Bundle saveInstanceState) {

    }
}