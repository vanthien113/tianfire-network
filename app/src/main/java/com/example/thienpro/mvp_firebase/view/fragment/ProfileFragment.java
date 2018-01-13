package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.Impl.ProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.activity.PostActivity;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class ProfileFragment extends Fragment implements ProfileView {
    private FragmentProfileBinding binding;
    private HomeAdapter homeAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProfilePresenter profilePresenter;
    private ArrayList<Post> listPost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        profilePresenter = new ProfilePresenterImpl(this);

        mLinearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        binding.rvProfile.setLayoutManager(mLinearLayoutManager);
        binding.rvProfile.setNestedScrollingEnabled(false);
        profilePresenter.loadPost();
        binding.setEvent(this);
        return binding.getRoot(); // Lưu ý: binding.getRoot();
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    public void loadData(){
        if (listPost != null){
            showLoading();
            binding.rvProfile.setLayoutFrozen(true);
            listPost.clear();
            profilePresenter.loadPost();
            binding.rvProfile.setLayoutFrozen(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) { // Hàm sẽ được chạy sau khi ấn sang tab Home
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
    }

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onPost() {
        Intent intent = new Intent(getContext(), PostActivity.class);
        startActivity(intent);
    }


    @Override
    public void showList(ArrayList<Post> list) {
        Collections.reverse(list);
        listPost = list;
        homeAdapter = new HomeAdapter(listPost, getContext());
        binding.rvProfile.setAdapter(homeAdapter);
        hideLoading();
        binding.rvProfile.setLayoutFrozen(false);
    }

    void hideLoading(){
        binding.pbLoading.setVisibility(View.GONE);
    }

    void showLoading() {
        binding.pbLoading.setVisibility(View.VISIBLE);
    }
}
