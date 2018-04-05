package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface HomePresenter extends BasePresenter<HomeView> {
    void loadAllListPost();

    void currentUser();

    void deletePost(Post post);

    void downloadImage(String imageUrl);
}
