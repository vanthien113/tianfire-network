package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.bases.BaseView;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface HomeView extends BaseView {
    void showAllPost(ArrayList<Post> list);

    void reloadPost();
}
