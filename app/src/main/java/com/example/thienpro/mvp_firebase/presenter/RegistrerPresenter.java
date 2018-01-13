package com.example.thienpro.mvp_firebase.presenter;

import android.net.Uri;

/**
 * Created by ThienPro on 11/28/2017.
 */

public interface RegistrerPresenter {
    void register(String email, String password, String name, String address, boolean sex, Uri filePath);
}
