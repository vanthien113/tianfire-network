package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.view.bases.BaseView;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostView extends BaseView{
    void onBackClick();

    void onPostClick();

    void onPostFail(Exception e);

    void navigationToHome();
}
