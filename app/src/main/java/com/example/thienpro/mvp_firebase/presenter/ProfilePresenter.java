package com.example.thienpro.mvp_firebase.presenter;

import android.content.Intent;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface ProfilePresenter extends BasePresenter<ProfileView>{
    void loadPost();

    void getUser();

    void deletePost(Post post);

    void searchUser(String userName);

    void downloadImage(String imageUrl);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
