package com.example.thienpro.mvp_firebase.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public static ProfileFragment newInstance(String text) {
        ProfileFragment f = new ProfileFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }

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

        return binding.getRoot(); // Lưu ý: binding.getRoot();
    }

    @Override
    public void onPost() {
        binding.tvLoading.setVisibility(View.VISIBLE); // Ẩn Loading...

        profilePresenter.newPost(binding.etPost.getText().toString());
        profilePresenter.loadPost();
        binding.etPost.setText("");

        binding.tvLoading.setVisibility(View.GONE); // Ẩn Loading...
    }

    @Override
    public void showList(ArrayList<Post> list) {
        listpost = list;

        for (int i = 0; i < 10; i++) {
            arrayList.add(listpost.get(i));
            Log.e("THIEN", "arrl " + i + " " + String.valueOf(arrayList.size()));
        }
        homeAdapter = new HomeAdapter(arrayList, (loadmore) this);

        binding.rvProfile.setAdapter(homeAdapter);

        binding.tvLoading.setVisibility(View.GONE); // Ẩn Loading...
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
                for (int i = homeAdapter.getItemCount(); (i < homeAdapter.getItemCount() + 10) && i < listpost.size(); i++) {
                    mlist.add(listpost.get(i));
                }
                homeAdapter.appendItem(mlist);
                isLoadMore = false;
            }
        }, 300);
    }
}
