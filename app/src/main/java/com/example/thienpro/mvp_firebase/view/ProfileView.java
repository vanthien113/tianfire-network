package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface ProfileView {
    void onPost();

    void showList(ArrayList<Post> list);
}
