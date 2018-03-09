package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.view.PostView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostPresenterImpl extends BasePresentermpl<PostView> implements PostPresenter {
    private PostInteractor postInteractor;

    public PostPresenterImpl() {
        postInteractor = new PostInteractorImpl();
    }

    @Override
    public void newPost(String content, Uri filePath) {
        getView().showLoadingDialog();

        postInteractor.writeNewPost(content, filePath, new PostInteractor.PostCallback() {
            @Override
            public void postListener(Exception e) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().navigationToHome();
                }
            }
        });
    }
}


