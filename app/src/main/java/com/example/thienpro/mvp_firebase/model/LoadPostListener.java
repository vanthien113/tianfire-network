package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThienPro on 11/17/2017.
 */

public interface LoadPostListener {
    void ListPost(ArrayList<Post> list);
}

