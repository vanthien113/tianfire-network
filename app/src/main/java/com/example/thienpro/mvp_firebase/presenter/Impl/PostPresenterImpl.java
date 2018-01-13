package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.net.Uri;

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

public class PostPresenterImpl implements PostPresenter, PostInteractor.loadPostListener {
    private PostInteractor postInteractor;
    private PostView postView;

    public PostPresenterImpl(PostView postView) {
        this.postView = postView;
        postInteractor = new PostInteractorImpl(this);
    }

    @Override
    public void newPost(String content, Uri filePath) {
        postInteractor.writeNewPost(content, filePath);
    }

    @Override
    public void postError(Exception e) {

    }

    @Override
    public void listPost(ArrayList<Post> list) {

    }

    @Override
    public void onPostFail(Exception e) {
        postView.onPostFail(e);
    }
}


