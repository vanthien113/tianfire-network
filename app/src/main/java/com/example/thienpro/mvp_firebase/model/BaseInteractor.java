package com.example.thienpro.mvp_firebase.model;

import android.net.Uri;

public interface BaseInteractor {
    void uploadImage(Uri uri, String child, final PostInteractor.GetStringCallback callback);
}
