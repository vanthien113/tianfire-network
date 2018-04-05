package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.bases.BaseView;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface ProfileView extends BaseView {
    void onPost();

    void showList(ArrayList<Post> list);

    void showSearchUser(ArrayList<User> list);

    void showUser(User user);

    void onChangeAvatar();

    void onChangeCover();

    void reloadPost();

    void showAvatarChanged(String avatarUrl);

    void showCoverChanged(String coverUrl);

    void onShowListPictureClick();

    void showLoading();

    void hideLoading();

    void onClearTextClick();

    void showChangeComplete();

    void showDeleteComplete();
}
