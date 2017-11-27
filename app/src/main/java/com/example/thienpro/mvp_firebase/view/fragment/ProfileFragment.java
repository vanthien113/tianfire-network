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
import com.example.thienpro.mvp_firebase.databinding.FragmentProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.view.ProfileView;
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

        profilePresenter = new ProfilePresenter(this);

        mLinearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        binding.rvProfile.setLayoutManager(mLinearLayoutManager);

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
            listPost.clear();
            profilePresenter.loadPost();
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
        if (binding.etPost.getText().toString().equals(""))
            Toast.makeText(getContext(), "Hãy nhập cảm nhận của bạn!", Toast.LENGTH_SHORT).show();
        else {
            binding.rvProfile.setLayoutFrozen(true); // Đóng băng recyclerview, tránh trường hợp lỗi touch khi sroll
            binding.tvLoading.setVisibility(View.VISIBLE); //  Loading...

            profilePresenter.newPost(binding.etPost.getText().toString());
            listPost.clear();
            profilePresenter.loadPost();
            binding.tvLoading.setVisibility(View.GONE);
            binding.etPost.setText("");
        }
    }


    @Override
    public void showList(ArrayList<Post> list) {
        Collections.reverse(list);
        listPost = list;
        homeAdapter = new HomeAdapter(listPost);
        binding.rvProfile.setAdapter(homeAdapter);

        binding.tvLoading.setVisibility(View.GONE); // Ẩn Loading...
        binding.rvProfile.setLayoutFrozen(false);
    }
}
