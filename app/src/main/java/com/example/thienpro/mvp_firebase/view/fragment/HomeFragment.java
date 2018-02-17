package com.example.thienpro.mvp_firebase.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.HomePresenterImpl;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class HomeFragment extends Fragment implements HomeView {
    private FragmentHomeBinding binding;
    private HomeAdapter homeAdapter;
    private android.support.v7.widget.LinearLayoutManager LinearLayoutManager;
    private HomePresenter homePresenter;
    private ArrayList<Post> listPost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        homePresenter = new HomePresenterImpl(this);
        homePresenter.loadAllListPost();

        LinearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), OrientationHelper.VERTICAL, false);
        binding.rvHome.setLayoutManager(LinearLayoutManager);
        binding.setEvent(this);
        return binding.getRoot();
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
            showLoading();
            binding.rvHome.setLayoutFrozen(true);
            listPost.clear();
            homePresenter.loadAllListPost();
        }
    }

    @Override
    public void showAllPost(ArrayList<Post> list) {
        hideLoading();
        Collections.reverse(list);
        listPost = list;

        homeAdapter = new HomeAdapter(listPost, getContext());
        binding.rvHome.setAdapter(homeAdapter);
        binding.rvHome.setLayoutFrozen(false);
    }

    @Override
    public void loadPostError(DatabaseError e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    void hideLoading() {
        binding.pbLoading.setVisibility(View.GONE);
    }

    void showLoading() {
        binding.pbLoading.setVisibility(View.VISIBLE);
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
