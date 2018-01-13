package com.example.thienpro.mvp_firebase.presenter;

import android.net.Uri;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostPresenter {
    void newPost(String content, Uri filePath);

    void postError(Exception e);
}
