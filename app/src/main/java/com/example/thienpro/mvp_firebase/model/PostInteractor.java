package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.Post;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface PostInteractor {
    void writeNewPost(String content);

    void LoadPersonalPost();

    void loadAllPost();

    interface LoadPostListener {
        void ListPost(ArrayList<Post> list);
    }
}
