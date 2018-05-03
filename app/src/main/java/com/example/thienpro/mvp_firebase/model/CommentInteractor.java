package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.Comment;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public interface CommentInteractor extends BaseInteractor {
    interface GetCommentCallBack {
        void onFinish(DatabaseError e, List<Comment> comments);
    }

    void writeComment(String userId, String content, String commentTime, String postTime, ExceptionCallback callback);

    void getComment(String postTime, GetCommentCallBack callBack);
}
