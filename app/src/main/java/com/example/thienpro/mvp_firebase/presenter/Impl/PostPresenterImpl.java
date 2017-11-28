package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.view.PostView;
import com.example.thienpro.mvp_firebase.view.ProfileView;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostPresenterImpl implements PostPresenter, PostInteractor.LoadPostListener {
    private PostInteractor postInteractor;
    private ProfileView profileView;

    public PostPresenterImpl(PostView postView) {
        this.profileView = profileView;
        postInteractor = new PostInteractorImpl(this);
    }

    public void newPost(String content) {
        postInteractor.writeNewPost(content);
    }

    @Override
    public void ListPost(ArrayList<Post> list) {

    }
}


