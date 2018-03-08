package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface EditInfoPresenter extends BasePresenter<EditInfoView>{
    void loadUser();
    void updateUser(String email, String name, String address, boolean sex, final String avatar, String cover);
}
