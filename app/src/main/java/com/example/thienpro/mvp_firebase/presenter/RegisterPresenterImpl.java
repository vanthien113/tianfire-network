package com.example.thienpro.mvp_firebase.presenter;

import android.content.Context;
import android.util.Log;

import com.example.thienpro.mvp_firebase.model.LoadUserListener;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.RegisterView;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class RegisterPresenterImpl implements LoadUserListener {
    private UserInteractor userInteractor;
    private RegisterView registerView;

    public RegisterPresenterImpl(RegisterView registerView, Context context) {
        this.registerView = registerView;
        userInteractor = new UserInteractor(this, context);
    }

    public void register(String email, String password, String repassword, String name, String address, boolean sex) {
        //TODO validate params la business cua View, ko phai cua Presenter
        if(email.equals("") || password.equals("") || repassword.equals("") || name.equals("") || address.equals(""))
            //TODO ko dc ep kieu nhu the nay.
            registerView.onRegisterNull((Context) registerView);
        if(password.equals(repassword)){
            userInteractor.register(email, password, name, address, sex);
            Log.e("THIEN", "g" +password + " " + repassword);
        }
        else registerView.onRePasswordFail((Context) registerView);
    }

    @Override
    public void getUser(User user) {
    }

    @Override
    public void navigationToHome() {
        registerView.navigationToHome((Context) registerView);
    }

    @Override
    public void onRegisterFail() {
        registerView.onRegisterEmailFail((Context) registerView);
    }

    @Override
    public void onSignInNull() {

    }

    @Override
    public void onLoginFail() {

    }
}
