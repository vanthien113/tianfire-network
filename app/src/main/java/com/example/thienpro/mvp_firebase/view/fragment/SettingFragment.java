package com.example.thienpro.mvp_firebase.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentSettingBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.SettingPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.SettingPresenter;
import com.example.thienpro.mvp_firebase.view.SettingView;
import com.example.thienpro.mvp_firebase.view.activity.AppSettingActivity;
import com.example.thienpro.mvp_firebase.view.activity.EditInfoActivity;
import com.example.thienpro.mvp_firebase.view.activity.LoginActivity;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class SettingFragment extends Fragment implements SettingView {
    private FragmentSettingBinding binding;
    private SettingPresenter presenter;

    @Override
    public void onResume() {
        super.onResume();
    }

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);

        binding.setEvent(this);
        presenter = new SettingPresenterImpl(this, getContext());

        return binding.getRoot();
    }

    @Override
    public void onEditInfoClick() {
        EditInfoActivity.startActivity(getContext());
    }

    @Override
    public void onLogout() {
        presenter.logOut();
    }

    @Override
    public void navigationToLogin() {
        LoginActivity.startActivity(getContext());
        getActivity().finish();
    }

    @Override
    public void onAppSettingClick() {
        AppSettingActivity.startActivity(getContext());
    }
}
