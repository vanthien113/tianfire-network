package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostInteractor extends BaseInteractor {
    interface ListPostCallback {
        void onFinish(DatabaseError e, List<Post> listPost);
    }

    interface GetPictureCallback {
        void onFinish(DatabaseError e, List<String> listPicture);
    }

    interface GetStringCallback {
        void onFinish(Exception e, String string);
    }

    void writeNewPost(String userName, String avatar, String content, String filePath, String currentTime, ExceptionCallback callback);

    void loadPersonalPost(String userId, ListPostCallback callback);

    void loadAllPost(ListPostCallback callback);

    void deletePost(Post post, ExceptionCallback callback);

    void editPost(Post post, ExceptionCallback callback);

    void getPicture(String userId, GetPictureCallback callback);

    void getFriendPost(String userId, ListPostCallback callback);
}
