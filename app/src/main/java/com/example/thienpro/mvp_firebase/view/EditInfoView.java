package com.example.thienpro.mvp_firebase.view;

/**
 * - Lớp View định nghĩa các hàm hiển thị dữ liệu, event
 */


import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.bases.BaseView;
import com.google.firebase.database.DatabaseError;

/**
 * Created by ThienPro on 11/11/2017.
 */

public interface EditInfoView extends BaseView{
    void onSaveClick();

    void getUser(User user);
}
