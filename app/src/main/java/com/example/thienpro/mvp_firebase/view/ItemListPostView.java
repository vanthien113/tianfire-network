package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.Post;

public interface ItemListPostView {
    void onImageClick();

    void onMenuClick(Post post);

    void onFriendProfileClick(Post post);
}
