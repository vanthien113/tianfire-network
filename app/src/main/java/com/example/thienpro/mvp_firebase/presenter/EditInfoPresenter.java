package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface EditInfoPresenter extends BasePresenter<EditInfoView>{
    void loadUser();
    void updateUser(final String name, final String address, final boolean sex);
}
