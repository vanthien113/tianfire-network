package com.example.thienpro.mvp_firebase.presenter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ThienPro on 11/16/2017.
 */

public interface ProfilePresenter {
    void writeNewPost(String id, String today, String post);
    ArrayList<String> getTime();
}
