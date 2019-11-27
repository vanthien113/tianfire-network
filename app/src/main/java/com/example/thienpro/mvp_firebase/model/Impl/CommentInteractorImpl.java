package com.example.thienpro.mvp_firebase.model.Impl;

import com.example.thienpro.mvp_firebase.model.CommentInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentInteractorImpl extends BaseInteractorImpl implements CommentInteractor {
    private DatabaseReference mDatabase;

    public CommentInteractorImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void writeComment(String userId, String content, String commentTime, String postTime, ExceptionCallback callback) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(USERID, userId);
        result.put(CONTENT, content);
        result.put(COMMENTTIME, commentTime);

        mDatabase.child(POSTS).child(postTime).child(COMMENTS).child(commentTime).updateChildren(result)
                .addOnSuccessListener(aVoid -> callback.onFinish(null))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    @Override
    public void getComment(String postTime, GetCommentCallBack callBack) {
        mDatabase.child(POSTS).child(postTime).child(COMMENTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Comment> list = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    list.add(dsp.getValue(Comment.class));
                }
                callBack.onFinish(null, list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFinish(databaseError, null);
            }
        });
    }

    @Override
    public void deleteComment(String commentTime, String postTime, ExceptionCallback callback) {
        mDatabase.child(POSTS).child(postTime).child(COMMENTS).child(commentTime).removeValue()
                .addOnSuccessListener(task -> callback.onFinish(null))
                .addOnFailureListener(e -> callback.onFinish(e));
    }
}
