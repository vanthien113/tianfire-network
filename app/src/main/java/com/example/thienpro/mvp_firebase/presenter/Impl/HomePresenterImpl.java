package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.view.HomeView;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class HomePresenterImpl implements PostInteractor.LoadPostListener, HomePresenter {
    private HomeView homeView;
    private PostInteractor postInteractor;

    public HomePresenterImpl(HomeView homeView) {
        this.homeView = homeView;
        postInteractor = new PostInteractorImpl(this);
    }

    public void loadAllListPost() {
        postInteractor.loadAllPost();
    }

    @Override
    public void ListPost(ArrayList<Post> list) {
        homeView.showAllPost(list);
    }
}
