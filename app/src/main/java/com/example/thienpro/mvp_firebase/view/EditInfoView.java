package com.example.thienpro.mvp_firebase.view;

/**
 * - Lớp View định nghĩa các hàm hiển thị dữ liệu, event
 */


import com.example.thienpro.mvp_firebase.view.bases.BaseView;

/**
 * Created by ThienPro on 11/11/2017.
 */

public interface EditInfoView extends BaseView {
    void onSaveClick();

    void onAddressClick();

    void showAddress(String address);

    void showChangeInfoComplete();
}
