package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.bases.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface FriendProfileView extends BaseView {
    void onShowListPictureClick();

    void showListPost(List<Post> listPost);

    void showUserInfomation(User user);

    void getFriendPost();
}
