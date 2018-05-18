package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.bases.BaseView;
import com.example.thienpro.mvp_firebase.model.entity.User;

import java.util.List;

public interface SearchUserView extends BaseView {
    void showUserSearched(List<User> userList);
}
