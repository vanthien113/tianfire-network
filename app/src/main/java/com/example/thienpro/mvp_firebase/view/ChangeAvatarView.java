package com.example.thienpro.mvp_firebase.view;

/**
 * Created by vanthien113 on 1/13/2018.
 */

public interface ChangeAvatarView {
    void onChangeAvatarClick();
    void onAvatar();

    void changeAvatarError(Exception e);
}
