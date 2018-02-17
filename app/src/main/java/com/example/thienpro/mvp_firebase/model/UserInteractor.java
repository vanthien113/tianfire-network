package com.example.thienpro.mvp_firebase.model;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.entity.User;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface UserInteractor {
    interface LoginCheck {
        void checker(boolean checker);
    }

    interface RegisterCheck {
        void checker(Exception checker);
    }

    interface VerifiEmailCheck {
        void checker(Exception checker, String email);
    }

    interface LogoutCheck {
        void checker(boolean checker);
    }

    interface GetUserListener {
        void getUser(User user);
    }

    interface AddAvatarListener {
        void addAvatar(Exception e);
    }

    interface UpdateUserListener {
        void updateUser(Exception e);
    }

    void sigIn(String email, String password, LoginCheck loginCheck);

    void signedInCheck(LoginCheck loginCheck);

    void logOut(LogoutCheck logoutCheck);

    void getUser(GetUserListener listener);

    void addAvatar(String email, final String name, String address, Boolean sex, Uri uri, AddAvatarListener addAvatarListener);

    void updateUser(String email, String name, String address, Boolean sex, UpdateUserListener updateUserListener);

    void verifiEmail(VerifiEmailCheck verifiEmailCheck);

    void register(final String email, String password, final String name, final String address, final boolean sex, RegisterCheck registerCheck);
}
