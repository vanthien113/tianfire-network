package com.example.thienpro.mvp_firebase.model;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostInteractor {
    interface PostListener {
        void postListener(Exception e);
    }

    interface ListPost {
        void listPost(DatabaseError e, ArrayList<Post> listPost);
    }

    void writeNewPost(String content, Uri filePath, PostListener postListener);

    void loadPersonalPost(ListPost listPost);

    void loadAllPost(ListPost listPost);

}
