package com.example.thienpro.mvp_firebase.model;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.entity.Post;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostInteractor {
    void writeNewPost(String content, Uri filePath);

    void loadPersonalPost();

    void loadAllPost();

    interface loadPostListener {
        void listPost(ArrayList<Post> list);

        void onPostFail(Exception e);
    }
}
