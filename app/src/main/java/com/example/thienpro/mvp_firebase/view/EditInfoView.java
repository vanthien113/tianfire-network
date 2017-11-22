package com.example.thienpro.mvp_firebase.view;

/**
 * - Lớp View định nghĩa các hàm hiển thị dữ liệu, event
 */


import android.content.Context;

import com.example.thienpro.mvp_firebase.model.entity.User;

/**
 * Created by ThienPro on 11/11/2017.
 */

public interface EditInfoView {
    void onSaveClick();

    void getUser(User user);

    void onNullInfoShow(Context context);

    void onSaveComplete(Context context);
}
