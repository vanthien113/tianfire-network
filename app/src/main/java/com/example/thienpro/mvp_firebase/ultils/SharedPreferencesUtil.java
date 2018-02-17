package com.example.thienpro.mvp_firebase.ultils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.thienpro.mvp_firebase.model.entity.User;

public class SharedPreferencesUtil {
    private SharedPreferences sharedPreferencesUtil;
    private User user;

    private static String DATA = "DATA";
    private static String ID = "id";
    private static String NAME = "userName";
    private static String ADDRESS = "address";
    private static String EMAIL = "email";
    private static String SEX = "sex";
    private static String AVATAR = "avatar";

    public SharedPreferencesUtil(Context context) {
        this.sharedPreferencesUtil = context.getSharedPreferences(DATA, Context.MODE_PRIVATE);
    }

    public void setUser(User user) {
        SharedPreferences.Editor editor = sharedPreferencesUtil.edit();
        editor.putString(ID, "ID");
        if (user.getName() != null)
            editor.putString(NAME, user.getName());
        if (user.getAddress() != null)
            editor.putString(ADDRESS, user.getAddress());
        if (user.getEmail() != null)
            editor.putString(EMAIL, user.getEmail());
        if (user.getSex() != null)
            editor.putBoolean(SEX, user.getSex());
        if (user.getAvatar() != null)
            editor.putString(AVATAR, user.getAvatar());
        editor.apply();
    }

    public User getUser() {
        user = new User();

        user.setName(sharedPreferencesUtil.getString(NAME, ""));
        user.setAddress(sharedPreferencesUtil.getString(ADDRESS, ""));
        user.setAvatar(sharedPreferencesUtil.getString(AVATAR, ""));
        user.setEmail(sharedPreferencesUtil.getString(EMAIL, ""));
        user.setSex(sharedPreferencesUtil.getBoolean(SEX, false));

        return user;
    }
}
