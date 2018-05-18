package com.example.thienpro.mvp_firebase.view;

import android.graphics.Bitmap;

import com.example.thienpro.mvp_firebase.bases.BaseView;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostView extends BaseView{
    void navigationToHome();

    void onDeleteImageClick();

    void showImageBitmap(Bitmap bitmap);

    void showDeleteImage();
}
