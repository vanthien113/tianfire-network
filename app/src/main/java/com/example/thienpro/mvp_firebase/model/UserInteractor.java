package com.example.thienpro.mvp_firebase.model;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.entity.User;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/28/2017.
 */
public interface UserInteractor {
    interface BooleanCheckCallback {
        void onFinish(boolean checker);
    }

    interface ExceptionCheckCallback {
        void onFinish(Exception checker);
    }

    interface UserCallback {
        void onFinish(DatabaseError error, User user);
    }

    interface StringCallback {
        void onFinish(Exception e, String string);
    }

    interface SearchUserCallBack {
        void onFinish(DatabaseError e, ArrayList<User> list);
    }

    void sigIn(String email, String password, ExceptionCheckCallback loginCheckCallback);

    void signedInCheck(BooleanCheckCallback callback);

    void logOut();

    void getUser(UserCallback callback, boolean loadUser);

    void addAvatar(final Uri uri, final StringCallback callback);

    void addCover(final Uri coverUri, StringCallback callback);

    void updateUser(final String name, String address, Boolean sex, final ExceptionCheckCallback callback);

    void verifiEmail(StringCallback callback);

    void register(final String email, String password, final String name, final String address, final boolean sex, ExceptionCheckCallback callback);

    void changePassword(String password, ExceptionCheckCallback callback);

    void forgotPassword(String email, ExceptionCheckCallback callback);

    void getFriendInfomation(String userId, UserCallback callback);

    void searchUser(String userName, SearchUserCallBack callBack);
}
