package com.example.thienpro.mvp_firebase.manager;

import com.example.thienpro.mvp_firebase.model.entity.User;

public interface UserManager {
    interface OnUserChangeListener {
        void onChange(User newUser);
    }

    void addOnUserChangeListener(OnUserChangeListener listener);

    void removeUserChangeListener(OnUserChangeListener listener);

    void logout();

    User getCurrentUser();

    void updateCurrentUser(User user);

    User getUser();
}
