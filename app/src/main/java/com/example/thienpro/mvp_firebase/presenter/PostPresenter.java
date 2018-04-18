package com.example.thienpro.mvp_firebase.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.view.PostView;
import com.example.thienpro.mvp_firebase.bases.BasePresenter;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostPresenter extends BasePresenter<PostView>{
    void newPost(String content);

    void onActivityResult(Context context, int requestCode, int resultCode, Intent data);

    void deleteImage();
}
