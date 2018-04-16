package com.example.thienpro.mvp_firebase.model;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.entity.User;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/28/2017.
 */
public interface UserInteractor {
    interface LoggedInCheckCallback {
        void checker(boolean checker);
    }

    interface LoginCheckCallback {
        void checker(boolean checker, Exception e);
    }

    interface RegisterCheckCallback {
        void checker(Exception checker);
    }

    interface VerifiEmailCheckCallback {
        void checker(Exception checker, String email);
    }

    interface LogoutCheckCallback {
        void checker(boolean checker);
    }

    interface GetUserCallback {
        void getUser(DatabaseError error, User user);
    }

    interface AddAvatarCallback {
        void addAvatar(Exception e, String uri);
    }

    interface AddCoverCallback {
        void addCover(Exception e, String uri);
    }

    interface UpdateUserCallback {
        void updateUser(Exception e);
    }

    interface ChangePasswordCallback {
        void changePasswordCallback(Exception e);
    }

    interface FriendInfomationCallback {
        void friendInfomation(DatabaseError e, User user);
    }

    interface SearchUserCallBack {
        void onFinish(DatabaseError e, ArrayList<User> list);
    }

    void sigIn(String email, String password, LoginCheckCallback loginCheckCallback);

    void signedInCheck(LoggedInCheckCallback callback);

    void logOut(LogoutCheckCallback callback);

    void getUser(GetUserCallback callback, boolean loadUser);

    void addAvatar(final Uri uri, final AddAvatarCallback callback);

    void addCover(final Uri coverUri, AddCoverCallback callback);

    void updateUser(final String name, String address, Boolean sex, final UpdateUserCallback callback);

    void verifiEmail(VerifiEmailCheckCallback callback);

    void register(final String email, String password, final String name, final String address, final boolean sex, RegisterCheckCallback callback);

    void changePassword(String password, ChangePasswordCallback callback);

    void forgotPassword(String email, ChangePasswordCallback callback);

    void getFriendInfomation(String userId, FriendInfomationCallback callback);

    void searchUser(String userName, SearchUserCallBack callBack);
}
