package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.google.firebase.database.DatabaseError;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class EditInfoPresenterImpl implements EditInfoPresenter {
    private EditInfoView view;
    private UserInteractor userInteractor;

    public EditInfoPresenterImpl(EditInfoView editInfoView) {
        this.view = editInfoView;
        userInteractor = new UserInteractorImpl();
    }

    public void loadUser() {
        view.showDialog();

        userInteractor.getUser(new UserInteractor.GetUserListener() {
            @Override
            public void getUser(DatabaseError e, User user) {
                view.hideDialog();
                if (e == null) {
                    view.getUser(user);
                } else {
                    view.getUserError(e);
                }
            }
        });
    }

    public void updateUser(String email, String name, String address, boolean sex) {
        view.showDialog();

        userInteractor.updateUser(email, name, address, sex, new UserInteractor.UpdateUserListener() {
            @Override
            public void updateUser(Exception e) {
                view.hideDialog();
                if (e != null) {
                    view.getUserError(e);
                } else {
                    view.updateSuccess();
                }
            }
        });
    }
}
