package com.example.thienpro.mvp_firebase.presenter;

import android.net.Uri;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface ProfilePresenter {
    void loadPost();

//    void getCurrentUser();

    void changeAvatar(Uri uri);

    void changeCover(Uri uri);

    void getUser();
}
