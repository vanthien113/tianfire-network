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
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.Impl.ProfilePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.ultils.LoadingDialog;
import com.example.thienpro.mvp_firebase.ultils.SharedPreferencesUtil;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.activity.PostActivity;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class ProfileFragment extends Fragment implements ProfileView {
    private FragmentProfileBinding binding;
    private HomeAdapter homeAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ProfilePresenter presenter;
    private ArrayList<Post> listPost;
    private LoadingDialog loadingDialog;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        presenter = new ProfilePresenterImpl(this, getContext());
        loadingDialog = new LoadingDialog(getContext());
        mLinearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);

        binding.rvProfile.setLayoutManager(mLinearLayoutManager);
        binding.rvProfile.setNestedScrollingEnabled(false);

        presenter.loadPost();
        presenter.getCurrentUser();

        binding.setEvent(this);
        return binding.getRoot(); // Lưu ý: binding.getRoot();
    }

    @Override
    public void onResume() {
        loadData();
        super.onResume();
    }

    public void loadData() {
        if (listPost != null) {
            binding.rvProfile.setLayoutFrozen(true);
            listPost.clear();
            presenter.loadPost();
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

    @Override
    public void onPost() {
        PostActivity.startActivity(getContext());
    }


    @Override
    public void showList(ArrayList<Post> list) {
        Collections.reverse(list);
        listPost = list;
        homeAdapter = new HomeAdapter(listPost, getContext());
        binding.rvProfile.setAdapter(homeAdapter);
        binding.rvProfile.setLayoutFrozen(false);
    }

    @Override
    public void loadPostError(DatabaseError e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }
}
