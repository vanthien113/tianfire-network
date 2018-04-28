package com.example.thienpro.mvp_firebase.manager.impl;

import android.content.SharedPreferences;

import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LocalUserManager implements UserManager {
    private SharedPreferences sharedPreferences;
    private List<OnUserChangeListener> observer = new ArrayList<>();
    private Gson gson;

    public LocalUserManager(Gson gson, SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    @Override
    public void updateCurrentUser(User user) {
        for (OnUserChangeListener listener : observer) {
            if (listener != null) {
                listener.onChange(user);
            }
        }
        sharedPreferences.edit().putString("user", gson.toJson(user)).apply();
    }

    public User getUser() {
        return gson.fromJson(sharedPreferences.getString("user", ""), User.class);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        User newUser = getUser();
        newUser.setAvatar(avatarUrl);

        updateCurrentUser(newUser);
    }

    @Override
    public void updateCover(String coverUrl) {
        User newUser = getUser();
        newUser.setCover(coverUrl);
        updateCurrentUser(newUser);
    }

    @Override
    public void logout() {
        sharedPreferences.edit().putString("user", "").apply();
    }

    @Override
    public void addOnUserChangeListener(OnUserChangeListener listener) {
        observer.add(listener);
    }

    @Override
    public void removeUserChangeListener(OnUserChangeListener listener) {
        observer.remove(listener);
    }
}
