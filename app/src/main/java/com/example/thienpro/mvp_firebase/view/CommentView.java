package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.bases.BaseView;
import com.example.thienpro.mvp_firebase.model.entity.Comment;

import java.util.List;

public interface CommentView extends BaseView {
    void onCommentClick();

    void showComments(List<Comment> comments);

    void clearComment();

    void showDeleteCommentMessage();
}
