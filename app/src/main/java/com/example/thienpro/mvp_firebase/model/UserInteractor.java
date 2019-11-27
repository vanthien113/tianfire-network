package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.User;
import com.google.firebase.database.DatabaseError;

import java.util.List;

/**
 * Created by ThienPro on 11/28/2017.
 */
public interface UserInteractor extends BaseInteractor {
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

    interface UsersCallBack {
        void onFinish(DatabaseError e, List<User> list);
    }

    void sigIn(String email, String password, ExceptionCheckCallback loginCheckCallback);

    void signedInCheck(BooleanCheckCallback callback);

    void logOut();

    void getUser(UserCallback callback, boolean loadUser);

    void addAvatar(String avatarUrl, ExceptionCheckCallback callback);

    void addCover(String coverUrl, ExceptionCheckCallback callback);

    void updateUser(String name, String address, Boolean sex, ExceptionCheckCallback callback);

    void verifiEmail(StringCallback callback);

    void register(String email, String password, String name, String address, boolean sex, ExceptionCheckCallback callback);

    void changePassword(String password, ExceptionCheckCallback callback);

    void forgotPassword(String email, ExceptionCheckCallback callback);

    void getFriendInfomation(String userId, UserCallback callback);

    void getAllUser(UsersCallBack callBack);
}
