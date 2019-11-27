package com.example.thienpro.mvp_firebase.view;

import com.example.thienpro.mvp_firebase.bases.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface PictureView extends BaseView {
    void showPicture(List<String> listPicture);

    void onChangeViewTypeClick();
}
