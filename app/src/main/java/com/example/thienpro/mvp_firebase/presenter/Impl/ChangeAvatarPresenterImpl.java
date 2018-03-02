package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.ChangeAvatarPresenter;
import com.example.thienpro.mvp_firebase.view.ChangeAvatarView;

/**
 * Created by vanthien113 on 1/13/2018.
 */

public class ChangeAvatarPresenterImpl implements ChangeAvatarPresenter {
    private ChangeAvatarView view;
    private UserInteractor userInteractor;

    public ChangeAvatarPresenterImpl(ChangeAvatarView changeAvatarView, Context context) {
        this.view = changeAvatarView;
        userInteractor = new UserInteractorImpl(context);
    }

    @Override
    public void changeAvatar(final Uri uri) {
        view.showLoading();

        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserListener() {
            @Override
            public void currentLocalUser(User user) {
                userInteractor.addAvatar(user.getEmail(), user.getName(), user.getAddress(), user.getSex(), uri, user.getCover(), new UserInteractor.AddAvatarListener() {
                    @Override
                    public void addAvatar(Exception e, String uri) {
                        view.hideLoading();
                        if (e != null) {
                            view.changeAvatarError(e);
                        } else {
                            view.navigationToHome();
                        }
                    }
                });
            }
        });
    }
}
