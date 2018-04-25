package com.example.thienpro.mvp_firebase.bases;

import android.support.annotation.Nullable;

/**
 * Created by User on 09/07/2017.
 */

public abstract class BasePresentermpl<V> implements BasePresenter<V> {
    private V view;

    @Nullable
    public V getView() {
        return view;
    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detach() {
        if (view != null) {
            view = null;
        }
    }
}
