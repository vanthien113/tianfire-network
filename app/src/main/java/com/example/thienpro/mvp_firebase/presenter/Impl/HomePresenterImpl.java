package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class HomePresenterImpl implements HomePresenter {
    private HomeView view;
    private PostInteractor postInteractor;
    private UserInteractor userInteractor;

    public HomePresenterImpl(HomeView homeView, Context context) {
        this.view = homeView;
        postInteractor = new PostInteractorImpl();
        userInteractor = new UserInteractorImpl(context);
    }

    public void loadAllListPost() {
        view.showLoading();

        postInteractor.loadAllPost(new PostInteractor.ListPostCallback() {
            @Override
            public void listPost(DatabaseError e, ArrayList<Post> listPost) {
                view.hideLoading();
                if (e == null) {
                    view.showAllPost(listPost);
                } else {
                    view.loadPostError(e);
                }
            }
        });
    }

    @Override
    public void currentUser() {
        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserCallback() {
            @Override
            public void currentLocalUser(User user) {
                view.currentUser(user);
            }
        });
    }

    @Override
    public void deletePost(Post post) {
        view.showLoading();

        postInteractor.deletePost(post, new PostInteractor.DeletePostCallback() {
            @Override
            public void listPost(Exception e) {
                view.hideLoading();

                if (e != null) {
                    view.showError(e);
                } else {
                    view.showMessenger("Đã xóa");
                }
            }
        });
    }
}
