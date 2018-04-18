package com.example.thienpro.mvp_firebase.model;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostInteractor {
    interface ExceptionCallback {
        void onFinish(Exception e);
    }

    interface ListPostCallback {
        void onFinish(DatabaseError e, ArrayList<Post> listPost);
    }

    interface GetPictureCallback {
        void onFinish(DatabaseError e, ArrayList<String> listPicture);
    }

    void writeNewPost(String content, Uri filePath, ExceptionCallback callback);

    void loadPersonalPost(ListPostCallback callback);

    void loadAllPost(ListPostCallback callback);

    void deletePost(Post post, ExceptionCallback callback);

    void editPost(Post post, ExceptionCallback callback);

    void getPicture(String userId, GetPictureCallback callback);

    void getFriendPost(String userId, ListPostCallback callback);
}
