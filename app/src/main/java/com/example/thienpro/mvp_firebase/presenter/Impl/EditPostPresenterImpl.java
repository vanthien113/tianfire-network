package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.EditPostPresenter;
import com.example.thienpro.mvp_firebase.view.EditPostView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

public class EditPostPresenterImpl extends BasePresentermpl<EditPostView> implements EditPostPresenter {
    private PostInteractor postInteractor;

    public EditPostPresenterImpl(PostInteractor postInteractor) {
        this.postInteractor = postInteractor;
    }

    @Override
    public void editPost(Post post) {
        getView().showLoadingDialog();

        postInteractor.editPost(post, new PostInteractor.EditPostCallback() {
            @Override
            public void editPost(Exception e) {
                getView().hideLoadingDialog();
                if (e != null) {
                } else {
                    getView().navigationToHome();
                }
            }
        });
    }
}
