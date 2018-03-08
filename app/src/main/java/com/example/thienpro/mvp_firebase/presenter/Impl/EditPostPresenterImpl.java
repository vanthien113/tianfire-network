package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.EditPostPresenter;
import com.example.thienpro.mvp_firebase.ultils.LogUltil;
import com.example.thienpro.mvp_firebase.view.EditPostView;

public class EditPostPresenterImpl implements EditPostPresenter {
    private PostInteractor postInteractor;
    private EditPostView view;

    public EditPostPresenterImpl(EditPostView view) {
        this.postInteractor = new PostInteractorImpl();
        this.view = view;
    }

    @Override
    public void editPost(Post post) {
        postInteractor.editPost(post, new PostInteractor.EditPostCallback() {
            @Override
            public void editPost(Exception e) {
//                view.
                if (e != null) {

                } else {
                    view.navigationToHome();
                    LogUltil.log(EditPostPresenterImpl.class, "RUN");
                }
            }
        });
    }
}
