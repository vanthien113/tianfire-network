package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.bases.BasePresenter;
import com.example.thienpro.mvp_firebase.model.entity.Comment;
import com.example.thienpro.mvp_firebase.view.CommentView;

public interface CommentPresenter extends BasePresenter<CommentView> {
    void writeComment(String content, String postTime);

    void getComment(String postTime);

    void deleteComment(Comment comment, String postTime);
}
