package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.ultils.LogUltil;
import com.example.thienpro.mvp_firebase.view.EditInfoView;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class EditInfoPresenterImpl implements EditInfoPresenter {
    private EditInfoView view;
    private UserInteractor userInteractor;

    public EditInfoPresenterImpl(EditInfoView editInfoView, Context context) {
        this.view = editInfoView;
        userInteractor = new UserInteractorImpl(context);
    }

    @Override
    public void loadUser() {
        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserListener() {
            @Override
            public void currentLocalUser(User user) {

                LogUltil.log(EditInfoPresenterImpl.class, user.getName());

                view.getUser(user);
            }
        });
    }

    @Override
    public void updateUser(final String email, final String name, final String address, final boolean sex, final String avatar, final String cover) {
        view.showDialog();

        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserListener() {
            @Override
            public void currentLocalUser(User user) {
                userInteractor.updateUser(email, name, address, sex, avatar, cover, new UserInteractor.UpdateUserListener() {
                    @Override
                    public void updateUser(Exception e) {
                        view.hideDialog();
                        if (e != null) {
                            view.getUserError(e);
                        } else {
                            view.updateSuccess();
                            userInteractor.saveCurrentLocalUser(new User(email, name, address, sex, avatar, cover));
                        }
                    }
                });
            }
        });
    }
}
