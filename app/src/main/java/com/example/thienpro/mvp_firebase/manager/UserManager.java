package com.example.thienpro.mvp_firebase.manager;

import com.example.thienpro.mvp_firebase.model.entity.User;

import java.util.List;

public interface UserManager {
    interface OnUserChangeListener {
        void onChange(User newUser);
    }

    void addOnUserChangeListener(OnUserChangeListener listener);

    void removeUserChangeListener(OnUserChangeListener listener);

    void logout();

    void updateCurrentUser(User user);

    User getUser();

    void updateAvatar(String avatarUrl);

    void updateCover(String coverUrl);

    interface OnListUserUpdated {
        void onChange(List<User> users);
    }

    void updateListUser(List<User> users);

    void addOnListUserUpdateListener(OnListUserUpdated listener);

    void removeListUserUpdateListener(OnListUserUpdated listener);

    User searchUser(String id);

    List<User> searchUserByName(String name);
}
