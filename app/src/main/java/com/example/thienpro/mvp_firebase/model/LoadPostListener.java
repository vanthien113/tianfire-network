package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.Post;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/17/2017.
 */

public interface LoadPostListener {
    ArrayList<Post> tomap(Post post);
}
