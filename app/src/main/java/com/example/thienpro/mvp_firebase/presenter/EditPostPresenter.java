package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.EditPostView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

public interface EditPostPresenter extends BasePresenter<EditPostView> {
    void editPost(Post post);
}
