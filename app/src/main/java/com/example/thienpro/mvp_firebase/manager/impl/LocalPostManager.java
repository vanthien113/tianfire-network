package com.example.thienpro.mvp_firebase.manager.impl;

import com.example.thienpro.mvp_firebase.manager.PostManager;

import java.util.ArrayList;
import java.util.List;

public class LocalPostManager implements PostManager {
    private List<PostManager.OnPostChangeListener> observer = new ArrayList<>();

    @Override
    public void postChange() {
        for (PostManager.OnPostChangeListener listener : observer) {
            if (listener != null) {
                listener.onChange();
            }
        }
    }

    @Override
    public void addOnPostChangeListener(OnPostChangeListener listener) {
        observer.add(listener);
    }

    @Override
    public void removePostChangeListener(OnPostChangeListener listener) {
        observer.remove(listener);
    }
}
