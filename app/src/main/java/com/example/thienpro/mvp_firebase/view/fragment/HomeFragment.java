package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.HomePresenterImpl;
import com.example.thienpro.mvp_firebase.ultils.LoadingDialog;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseFragment;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements HomeView {
    private HomeAdapter homeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private HomePresenter presenter;
    private ArrayList<Post> listPost;
    private LoadingDialog loadingDialog;

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
        loadingDialog = new LoadingDialog(getContext());
        presenter = new HomePresenterImpl(this);
        linearLayoutManager = new LinearLayoutManager(viewDataBinding.getRoot().getContext(), OrientationHelper.VERTICAL, false);

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

    @Override
    protected void attach(Context context) {

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

        homeAdapter = new HomeAdapter(listPost, getContext());
        viewDataBinding.rvHome.setAdapter(homeAdapter);
        viewDataBinding.rvHome.setLayoutFrozen(false);
    }

    @Override
    public void loadPostError(DatabaseError e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
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

}
