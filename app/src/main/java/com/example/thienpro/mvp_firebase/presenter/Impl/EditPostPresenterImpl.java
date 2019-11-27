package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.manager.PostManager;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.EditPostPresenter;
import com.example.thienpro.mvp_firebase.view.EditPostView;

public class EditPostPresenterImpl extends BasePresentermpl<EditPostView> implements EditPostPresenter {
    private PostInteractor postInteractor;
    private PostManager postManager;

    public EditPostPresenterImpl(PostInteractor postInteractor, PostManager postManager) {
        this.postInteractor = postInteractor;
        this.postManager = postManager;
    }

    @Override
    public void editPost(Post post) {
        if (getView() == null)
            return;
        getView().showLoadingDialog();

        postInteractor.editPost(post, e -> {
            if (getView() == null)
                return;
            getView().hideLoadingDialog();
            if (e != null) {
            } else {
                postManager.postChange();
                getView().navigationToHome();
            }
        });
    }
}
