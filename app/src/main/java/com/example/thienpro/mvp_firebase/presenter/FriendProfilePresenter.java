package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.FriendProfileView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

public interface FriendProfilePresenter extends BasePresenter<FriendProfileView> {
    void getFriendInfomation(String userId);

    void getFriendPost(String userId);
}
