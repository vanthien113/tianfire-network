package com.example.thienpro.mvp_firebase.presenter;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.LoadUserListener;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.EditInfoView;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class EditInfoPresenter implements LoadUserListener{
    private EditInfoView editInfoView;
    private UserInteractor userInteractor;

    public EditInfoPresenter(EditInfoView editInfoView, Context context) {
        this.editInfoView = editInfoView;
        userInteractor = new UserInteractor(this, context);
    }

    public void loadUser(){
        userInteractor.getUser();
    }

    public void updateUser(String email, String name, String address, boolean sex){
        if(email.equals("") || name.equals("") || address.equals(""))
            editInfoView.onNullInfoShow((Context) editInfoView);
        else{
            userInteractor.updateUser(email, name, address, sex);
            editInfoView.onSaveComplete((Context) editInfoView);
        }
    }

    @Override
    public void getUser(User user) {
        editInfoView.getUser(user);
    }

    @Override
    public void navigationToHome() {

    }

    @Override
    public void onRegisterFail() {

    }

    @Override
    public void onSignInNull() {

    }

    @Override
    public void onLoginFail() {

    }
}
