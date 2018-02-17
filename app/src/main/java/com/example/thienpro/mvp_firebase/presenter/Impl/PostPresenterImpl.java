package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.view.PostView;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostPresenterImpl implements PostPresenter {
    private PostInteractor postInteractor;
    private PostView view;

    public PostPresenterImpl(PostView postView) {
        this.view = postView;
        postInteractor = new PostInteractorImpl();
    }

    @Override
    public void newPost(String content, Uri filePath) {
        view.showLoading();

        postInteractor.writeNewPost(content, filePath, new PostInteractor.PostListener() {
            @Override
            public void postListener(Exception e) {
                view.hideLoading();
                if (e != null) {
                    view.onPostFail(e);
                } else {
                    view.navigationToHome();
                }
            }
        });
    }
}


