package com.example.thienpro.mvp_firebase.presenter;

import android.content.Intent;
import android.net.Uri;

import com.example.thienpro.mvp_firebase.view.PostView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostPresenter extends BasePresenter<PostView>{
    void newPost(String content);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void deleteImage();
}
