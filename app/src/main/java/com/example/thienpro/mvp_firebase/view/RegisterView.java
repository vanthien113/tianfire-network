package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.view.bases.BaseView;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface RegisterView extends BaseView {
    void navigationToVerifiEmail();

    void onNextClick();

    void onBackClick();
}
