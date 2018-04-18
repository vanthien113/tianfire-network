package com.example.thienpro.mvp_firebase.bases;

import com.google.firebase.database.DatabaseError;

public interface BaseView {
    void showExceptionError(Exception e);

    void showDatabaseError(DatabaseError error);

    void showMessenger(String messenger);

    void showLoadingDialog();

    void hideLoadingDialog();
}
