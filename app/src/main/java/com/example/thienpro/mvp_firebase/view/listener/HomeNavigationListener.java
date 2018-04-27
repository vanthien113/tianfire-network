package com.example.thienpro.mvp_firebase.view.listener;

import com.example.thienpro.mvp_firebase.model.entity.Post;

public interface HomeNavigationListener {
    void navigationToEditPostActivity(Post post);

    void navigationToFriendProfileActivity(String userId);

    void navigationToPictureActivity(String userId);

    void navigationToPostActivity();

    void navigationToChangePasswordActivity();

    void navigationToAppSettingActivity();

    void navigationToLoginActivity();

    void navigationToEditInfoActivity();

    void navigationToSearchActivity();

    void navigationToImageZoomActivity(String imageUrl);
}
