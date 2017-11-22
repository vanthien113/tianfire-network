package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.model.LoadPostListener;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.HomeView;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class HomePresenter implements LoadPostListener{
    private HomeView homeView;
    private PostInteractor postInteractor;

    public HomePresenter(HomeView homeView) {
        this.homeView = homeView;
        postInteractor = new PostInteractor(this);
    }

    public void loadAllListPost(){
        postInteractor.loadAllPost();
    }

    @Override
    public void ListPost(ArrayList<Post> list) {
        homeView.showAllPost(list);
    }
}
