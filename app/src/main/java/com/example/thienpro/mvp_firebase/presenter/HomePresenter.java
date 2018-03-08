package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.model.entity.Post;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface HomePresenter {
    void loadAllListPost();

    void currentUser();

    void deletePost(Post post);
}
