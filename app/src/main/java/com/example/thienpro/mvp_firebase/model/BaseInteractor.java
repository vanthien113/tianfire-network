package com.example.thienpro.mvp_firebase.model;

import android.net.Uri;

public interface BaseInteractor {
    void uploadImage(Uri uri, String child, String userId, PostInteractor.GetStringCallback callback);

    interface ExceptionCallback {
        void onFinish(Exception e);
    }
}
