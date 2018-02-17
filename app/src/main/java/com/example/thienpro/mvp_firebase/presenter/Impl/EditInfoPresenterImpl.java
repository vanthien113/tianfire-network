package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.view.EditInfoView;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class EditInfoPresenterImpl implements EditInfoPresenter {
    private EditInfoView editInfoView;
    private UserInteractor userInteractor;

    public EditInfoPresenterImpl(EditInfoView editInfoView) {
        this.editInfoView = editInfoView;
        userInteractor = new UserInteractorImpl();
    }

    public void loadUser() {
        userInteractor.getUser(new UserInteractor.GetUserListener() {
            @Override
            public void getUser(User user) {
                editInfoView.getUser(user);
            }
        });
    }

    public void updateUser(String email, String name, String address, boolean sex) {
        userInteractor.updateUser(email, name, address, sex, new UserInteractor.UpdateUserListener() {
            @Override
            public void updateUser(Exception e) {
                if (e != null) {

                }
            }
        });
    }
}
