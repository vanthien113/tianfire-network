package com.example.thienpro.mvp_firebase.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.FragmentSettingBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.SettingPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.SettingPresenter;
import com.example.thienpro.mvp_firebase.view.SettingView;
import com.example.thienpro.mvp_firebase.view.activity.AppSettingActivity;
import com.example.thienpro.mvp_firebase.view.activity.ChangePasswordActivity;
import com.example.thienpro.mvp_firebase.view.activity.EditInfoActivity;
import com.example.thienpro.mvp_firebase.view.activity.LoginActivity;
import com.example.thienpro.mvp_firebase.view.bases.BaseFragment;

/**
 * Created by ThienPro on 11/22/2017.
 */

public class SettingFragment extends BaseFragment<FragmentSettingBinding> implements SettingView {
    private SettingPresenter presenter;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void init(@Nullable View view) {
        viewDataBinding.setEvent(this);

        presenter = new SettingPresenterImpl(getContext());
        presenter.attachView(this);
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

    @Override
    public void onChangePasswordClick() {
        ChangePasswordActivity.startActivity(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void attach(Context context) {

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
