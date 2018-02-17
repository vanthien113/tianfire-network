package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
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

    public HomePresenterImpl(HomeView homeView) {
        this.view = homeView;
        postInteractor = new PostInteractorImpl();
    }

    public void loadAllListPost() {
        view.showLoading();

        postInteractor.loadAllPost(new PostInteractor.ListPost() {
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
}
