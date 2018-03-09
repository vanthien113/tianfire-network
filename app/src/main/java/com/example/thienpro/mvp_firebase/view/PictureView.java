package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.view.bases.BaseView;

import java.util.ArrayList;

public interface PictureView extends BaseView {
    void showPicture(ArrayList<String> listPicture);

    void onChangeViewTypeClick();
}
