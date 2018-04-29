package com.example.thienpro.mvp_firebase.model;

import android.net.Uri;

public interface BaseInteractor {
    void uploadImage(Uri uri, String child, String userId, final PostInteractor.GetStringCallback callback);
}
