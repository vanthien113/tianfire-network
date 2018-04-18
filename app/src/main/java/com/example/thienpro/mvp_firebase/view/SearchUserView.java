package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.bases.BaseView;

import java.util.ArrayList;

public interface SearchUserView extends BaseView {
    void showUserSearched(ArrayList<User> userList);
}
