package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.ChangeAvatarPresenter;
import com.example.thienpro.mvp_firebase.view.ChangeAvatarView;
import com.google.firebase.database.DatabaseError;

/**
 * Created by vanthien113 on 1/13/2018.
 */

public class ChangeAvatarPresenterImpl implements ChangeAvatarPresenter {
    private ChangeAvatarView view;
    private UserInteractor userInteractor;

    public ChangeAvatarPresenterImpl(ChangeAvatarView changeAvatarView) {
        this.view = changeAvatarView;
        userInteractor = new UserInteractorImpl();
    }

    @Override
    public void changeAvatar(final Uri uri) {
        view.showLoading();

        userInteractor.getUser(new UserInteractor.GetUserListener() {
            @Override
            public void getUser(DatabaseError error, User user) {
                if (error != null) {
                    view.changeAvatarError(error);
                } else {
                    userInteractor.addAvatar(user.getEmail(), user.getName(), user.getAddress(), user.getSex(), uri, new UserInteractor.AddAvatarListener() {
                        @Override
                        public void addAvatar(Exception e) {
                            view.hideLoading();

                            if (e != null) {
                                view.changeAvatarError(e);
                            } else {
                                view.navigationToHome();
                            }
                        }
                    });
                }
            }
        });
    }
}
