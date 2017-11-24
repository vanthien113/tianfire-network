package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentSettingBinding;
import com.example.thienpro.mvp_firebase.view.SettingView;
import com.example.thienpro.mvp_firebase.view.activity.EditInfoActivity;
import com.example.thienpro.mvp_firebase.view.activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class SettingFragment extends android.support.v4.app.DialogFragment implements SettingView {
    private FragmentSettingBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);

        binding.setEvent(this);

        return binding.getRoot();
    }

    @Override
    public void onEditinfoClick() {
        Intent intent = new Intent(getContext(), EditInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLogout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
