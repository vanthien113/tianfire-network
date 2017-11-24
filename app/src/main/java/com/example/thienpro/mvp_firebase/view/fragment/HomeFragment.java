package com.example.thienpro.mvp_firebase.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.example.thienpro.mvp_firebase.view.adapters.loadmore;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class HomeFragment extends android.support.v4.app.DialogFragment implements HomeView, loadmore {
    private FragmentHomeBinding binding;
    private HomeAdapter homeAdapter;
    private android.support.v7.widget.LinearLayoutManager LinearLayoutManager;
    private HomePresenter homePresenter;
    private ArrayList<Post> listpost;
    private ArrayList<Post> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        listpost = new ArrayList<>();
        arrayList = new ArrayList<>();

        homePresenter = new HomePresenter(this);
        homePresenter.loadAllListPost();

        LinearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext());
        LinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvHome.setLayoutManager(LinearLayoutManager);

        homeAdapter = new HomeAdapter(arrayList, this);
        binding.rvHome.setAdapter(homeAdapter);

        binding.setEvent(this);
        return binding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) { // Hàm sẽ được chạy sau khi ấn sang tab Home
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            arrayList.clear();
            homePresenter.loadAllListPost();
        }
    }

    @Override
    public void showAllPost(ArrayList<Post> list) {
        binding.tvLoading.setVisibility(View.GONE);
        listpost = list;

        for (int i = listpost.size() - 1; i > listpost.size() - 11 && i >= 0; i--) {
            arrayList.add(listpost.get(i));
        }
        homeAdapter.notifyDataSetChanged();
    }

    private boolean isLoadMore = false;

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
                int position = listpost.size() - homeAdapter.getItemCount() - 1;
                for (int i = position; (i > position - 10) && i >= 0; i--) {
                    mlist.add(listpost.get(i));
                }
                homeAdapter.appendItem(mlist);
                isLoadMore = false;
            }
        }, 300);
    }
}