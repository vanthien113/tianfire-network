package com.example.thienpro.mvp_firebase.view.bases;

public interface BasePresenter<V> {
    void attachView(V view);

    void detach();
}
