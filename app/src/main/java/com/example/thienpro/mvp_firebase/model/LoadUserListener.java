package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface LoadUserListener {
    void onLoadUserInfo(User user);
}
