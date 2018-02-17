package com.example.thienpro.mvp_firebase.view;

import com.google.firebase.database.DatabaseError;

/**
 * Created by vanthien113 on 1/13/2018.
 */

public interface ChangeAvatarView {
    void onChangeAvatarClick();

    void onAvatar();

    void changeAvatarError(Exception e);

    void changeAvatarError(DatabaseError e);

    void navigationToHome();

    void showLoading();

    void hideLoading();
}
