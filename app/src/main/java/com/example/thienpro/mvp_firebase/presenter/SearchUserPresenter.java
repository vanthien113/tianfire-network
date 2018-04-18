package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.SearchUserView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

public interface SearchUserPresenter extends BasePresenter<SearchUserView> {
    void search(String useName);
}
