package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.RegisterView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface RegisterPresenter extends BasePresenter<RegisterView> {
    void register(String email, String password, String name, String address, boolean sex);
}
