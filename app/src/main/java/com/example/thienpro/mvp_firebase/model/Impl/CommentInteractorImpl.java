package com.example.thienpro.mvp_firebase.model.Impl;

import android.support.annotation.NonNull;

import com.example.thienpro.mvp_firebase.model.CommentInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Comment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentInteractorImpl extends BaseInteractorImpl implements CommentInteractor {
    private DatabaseReference mDatabase;

    public CommentInteractorImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void writeComment(String userId, String content, String commentTime, String postTime, final ExceptionCallback callback) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("content", content);
        result.put("commentTime", commentTime);

        mDatabase.child(POSTS).child(postTime).child(COMMENTS).child(commentTime).updateChildren(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onFinish(null);
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFinish(e);
                    }
                });
    }

    @Override
    public void getComment(String postTime, final GetCommentCallBack callBack) {
        mDatabase.child(POSTS).child(postTime).child(COMMENTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Comment> list = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Comment comment = dsp.getValue(Comment.class);
                    list.add(comment);
                }
                callBack.onFinish(null, list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFinish(databaseError, null);
            }
        });
    }
}
