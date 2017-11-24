package com.example.thienpro.mvp_firebase.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.adapters.loadmore;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class ProfileFragment extends android.support.v4.app.DialogFragment implements ProfileView, loadmore {
    private FragmentProfileBinding binding;
    private ArrayList<Post> listpost;
    private ArrayList<Post> arrayList;
    private boolean isLoadMore = false;
    private HomeAdapter homeAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProfilePresenterImpl profilePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        profilePresenter = new ProfilePresenterImpl(this);

        listpost = new ArrayList<>();
        arrayList = new ArrayList<>();

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvProfile.setLayoutManager(mLinearLayoutManager);

        profilePresenter.loadPost();
        binding.setEvent(this);

        homeAdapter = new HomeAdapter(arrayList, this);
        binding.rvProfile.setAdapter(homeAdapter);

        return binding.getRoot(); // Lưu ý: binding.getRoot();
    }

    @Override
    public void onPost() {
        binding.rvProfile.setLayoutFrozen(true); // Đóng băng recyclerview, tránh trường hợp lỗi touch khi sroll
        binding.tvLoading.setVisibility(View.VISIBLE); //  Loading...
        profilePresenter.newPost(binding.etPost.getText().toString());

        arrayList.clear();
        listpost.clear();

        profilePresenter.loadPost();
        binding.tvLoading.setVisibility(View.GONE);

        binding.etPost.setText("");
    }

    @Override
    public void showList(ArrayList<Post> list) {
        listpost = list;

        for (int i= listpost.size() - 1; i > listpost.size() - 10 && i>= 0; i--) {
            arrayList.add(listpost.get(i));
        }

        homeAdapter.notifyDataSetChanged();
        binding.tvLoading.setVisibility(View.GONE); // Ẩn Loading...
        binding.rvProfile.setLayoutFrozen(false);
    }

    @Override
    public void onNullContent() {
        Toast.makeText(getContext(), "Hãy nhập cảm nhận của bạn!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadmore() {
        if (isLoadMore) {
            return;
        }
        isLoadMore = true;
        Handler handler = new Handler();
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Post> mlist = new ArrayList<>();
                for (int i = listpost.size() - homeAdapter.getItemCount() - 1; (i > listpost.size() - homeAdapter.getItemCount() - 9) && i >= 0; i--) {
                    mlist.add(listpost.get(i));
                }
                homeAdapter.appendItem(mlist);
                isLoadMore = false;
            }
        }, 300);
    }
}
