package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.bases.BaseView;
import com.example.thienpro.mvp_firebase.model.entity.Post;

import java.util.List;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface ProfileView extends BaseView {
    void showListPost(List<Post> listPost);

    void showLoading();

    void hideLoading();

    void showChangeComplete();

    void showDeleteComplete();

    void onUserUpdated();

    void onSearchClick();
}
