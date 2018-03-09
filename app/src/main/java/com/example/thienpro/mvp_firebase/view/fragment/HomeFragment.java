package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.HomePresenterImpl;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.activity.EditPostActivity;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements HomeView, HomeAdapter.ListPostMenuListener {
    private HomeAdapter homeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private HomePresenter presenter;
    private ArrayList<Post> listPost;
    private User user;

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
        presenter = new HomePresenterImpl(getContext());
        presenter.attachView(this);

        linearLayoutManager = new LinearLayoutManager(viewDataBinding.getRoot().getContext(), OrientationHelper.VERTICAL, false);

        presenter.currentUser();
        presenter.loadAllListPost();

        viewDataBinding.rvHome.setLayoutManager(linearLayoutManager);
        viewDataBinding.setEvent(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) { // Hàm sẽ được chạy sau khi ấn sang tab Home
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }


    public void loadData() {
        if (listPost != null) {
            viewDataBinding.rvHome.setLayoutFrozen(true);
            listPost.clear();
            presenter.loadAllListPost();
        }
    }

    @Override
    public void showAllPost(ArrayList<Post> list) {
        Collections.reverse(list);
        listPost = list;

        homeAdapter = new HomeAdapter(listPost, getContext(), this, user);
        viewDataBinding.rvHome.setAdapter(homeAdapter);
        viewDataBinding.rvHome.setLayoutFrozen(false);
    }

    @Override
    public void currentUser(User user) {
        this.user = user;
    }

    @Override
    public void reloadPost() {
        loadData();
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
    protected void screenStart(@Nullable Bundle saveInstanceState) {

    }

    @Override
    protected void attach(Context context) {

    }

}
