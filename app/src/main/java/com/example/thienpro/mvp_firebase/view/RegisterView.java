package com.example.thienpro.mvp_firebase.view;

import android.content.Context;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface RegisterView {
    void navigationToVerifiEmail(Context context);

    void onRegisterClick();

    void onRegisterEmailFail(Context context);
}
