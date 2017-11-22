package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.Post;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface HomeView {
    void onSignOutClick();

    void navigationToMain();

    void navigationToProfile();

    void navigationToEditInfo();

    void showAllPost(ArrayList<Post> list);
}
