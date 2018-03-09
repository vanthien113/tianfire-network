package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.view.ListLocationView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresenter;

public interface ListLocationPresenter extends BasePresenter<ListLocationView>{
    void getListLocation();
}
