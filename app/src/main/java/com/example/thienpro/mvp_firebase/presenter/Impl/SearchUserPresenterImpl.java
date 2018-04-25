package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.text.TextUtils;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.SearchUserPresenter;
import com.example.thienpro.mvp_firebase.view.SearchUserView;
import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class SearchUserPresenterImpl extends BasePresentermpl<SearchUserView> implements SearchUserPresenter {
    private UserInteractor userInteractor;

    public SearchUserPresenterImpl(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    @Override
    public void search(String useName) {
        if (getView() == null)
            return;
        if (TextUtils.isEmpty(useName)) {
            getView().showUserSearched(null);
            return;
        }
        userInteractor.searchUser(useName, new UserInteractor.SearchUserCallBack() {
            @Override
            public void onFinish(DatabaseError e, ArrayList<User> list) {
                if (getView() == null) {
                    return;
                }
                if (e != null) {
                    getView().showDatabaseError(e);
                } else {
                    getView().showUserSearched(list);
                }
            }
        });
    }
}
