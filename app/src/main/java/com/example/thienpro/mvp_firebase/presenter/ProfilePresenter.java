package com.example.thienpro.mvp_firebase.presenter;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface ProfilePresenter extends BasePresenter<ProfileView>{
    void loadPost();

    void changeAvatar(Uri uri);

    void changeCover(Uri uri);

    void getUser();

    void deletePost(Post post);
}
