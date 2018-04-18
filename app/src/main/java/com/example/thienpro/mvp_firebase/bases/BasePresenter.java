package com.example.thienpro.mvp_firebase.bases;

public interface BasePresenter<V> {
    void attachView(V view);

    void detach();
}
