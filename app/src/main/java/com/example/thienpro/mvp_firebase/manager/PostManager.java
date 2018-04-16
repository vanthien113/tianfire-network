package com.example.thienpro.mvp_firebase.manager;

public interface PostManager {
    interface OnPostChangeListener {
        void onChange();
    }

    void postChange();

    void addOnPostChangeListener(OnPostChangeListener listener);

    void removePostChangeListener(OnPostChangeListener listener);
}
