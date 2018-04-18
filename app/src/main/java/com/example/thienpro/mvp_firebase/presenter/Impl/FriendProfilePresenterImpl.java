package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.FriendProfilePresenter;
import com.example.thienpro.mvp_firebase.view.FriendProfileView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class FriendProfilePresenterImpl extends BasePresentermpl<FriendProfileView> implements FriendProfilePresenter {
    private UserInteractor userInteractor;
    private PostInteractor postInteractor;

    public FriendProfilePresenterImpl(UserInteractor userInteractor, PostInteractor postInteractor) {
        this.userInteractor = userInteractor;
        this.postInteractor = postInteractor;
    }

    @Override
    public void getFriendInfomation(String userId) {
        if (getView() == null)
            return;
        getView().showLoadingDialog();

        userInteractor.getFriendInfomation(userId, new UserInteractor.FriendInfomationCallback() {
            @Override
            public void friendInfomation(DatabaseError e, User user) {
                if (getView() == null)
                    return;
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showDatabaseError(e);
                } else {
                    getView().showUserInfomation(user);
                    getView().getFriendPost();
                }
            }
        });
    }

    @Override
    public void getFriendPost(String userId) {
        if (getView() == null)
            return;
        getView().showLoadingDialog();
        postInteractor.getFriendPost(userId, new PostInteractor.FriendPostCallback() {
            @Override
            public void friendPost(DatabaseError e, ArrayList<Post> post) {
                if (getView() == null)
                    return;
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showDatabaseError(e);
                } else {
                    getView().showListPost(post);
                }
            }
        });
    }
}
