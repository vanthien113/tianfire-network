package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.ChangePasswordView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

public interface ChangePasswordPresenter extends BasePresenter<ChangePasswordView> {
    void changePassword(String password);
}
