package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.bases.BaseFragment;
import com.example.thienpro.mvp_firebase.databinding.FragmentHomeBinding;
import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.ultils.DownloadUltil;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.activity.EditPostActivity;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.listener.HomeNavigationListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements HomeView, HomeAdapter.ListPostMenuListener {
    private HomeAdapter homeAdapter;
    private HomePresenter presenter;
    private HomeNavigationListener navigationListener;
    private UserManager userManager;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(@Nullable View view) {
        presenter = getAppComponent().getCommonComponent().getHomePresenter();
        presenter.attachView(this);

        userManager = getAppComponent().getUserManager();

        presenter.loadAllListPost();

        getBinding().rvHome.setLayoutManager(LayoutUltils.getLinearLayoutManager(getContext()));
        getBinding().setEvent(this);

        getBinding().srlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadAllListPost();
                getBinding().srlHome.setRefreshing(false);
            }
        });
    }

    @Override
    public void showAllPost(ArrayList<Post> list) {
        Collections.reverse(list);

        homeAdapter = new HomeAdapter(list, getContext(), this, userManager.getUser());
        getBinding().rvHome.setAdapter(homeAdapter);
    }

    @Override
    public void reloadPost() {
        presenter.loadAllListPost();
    }

    @Override
    public void showLoadingPb() {
        getBinding().srlHome.setRefreshing(true);
    }

    @Override
    public void hideLoadingPb() {
        getBinding().srlHome.setRefreshing(false);
    }

    @Override
    public void showDeteleComplete() {
        showToastMessage(R.string.da_xoa);
    }

    @Override
    public void onEditPost(Post post) {
        EditPostActivity.start(getContext(), post);
    }

    @Override
    public void onDeletePost(Post post) {
        presenter.deletePost(post);
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
    }

    @Override
    protected void screenStart(@Nullable Bundle saveInstanceState) {

    }

    @Override
    protected void attach(Context context) {
        if (context instanceof HomeNavigationListener) {
            navigationListener = (HomeNavigationListener) context;
        }
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
}
