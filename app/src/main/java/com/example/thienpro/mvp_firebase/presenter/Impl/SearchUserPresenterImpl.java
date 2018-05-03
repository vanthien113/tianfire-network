package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.SearchUserPresenter;
import com.example.thienpro.mvp_firebase.view.SearchUserView;

import java.util.ArrayList;
import java.util.List;

public class SearchUserPresenterImpl extends BasePresentermpl<SearchUserView> implements SearchUserPresenter {
    private UserManager userManager;

    public SearchUserPresenterImpl(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void search(String useName) {
        if (getView() == null)
            return;

        List<User> list = userManager.searchUserByName(useName);

        getView().showUserSearched(list);
    }
}
