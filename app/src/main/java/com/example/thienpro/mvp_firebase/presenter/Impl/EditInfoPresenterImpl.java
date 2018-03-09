package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class EditInfoPresenterImpl extends BasePresentermpl<EditInfoView> implements EditInfoPresenter {
    private UserInteractor userInteractor;

    public EditInfoPresenterImpl(Context context) {
        userInteractor = new UserInteractorImpl(context);
    }

    @Override
    public void loadUser() {
        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserCallback() {
            @Override
            public void currentLocalUser(User user) {
                getView().getUser(user);
            }
        });
    }

    @Override
    public void updateUser(final String name, final String address, final boolean sex) {
        getView().showLoadingDialog();

        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserCallback() {
            @Override
            public void currentLocalUser(User user) {
                userInteractor.updateUser(name, address, sex, new UserInteractor.UpdateUserCallback() {
                    @Override
                    public void updateUser(Exception e) {
                        getView().hideLoadingDialog();
                        if (e != null) {
                            getView().showExceptionError(e);
                        } else {
                            getView().showMessenger("Cập nhật thành công!");
                            userInteractor.saveCurrentLocalUser(new User(null, name, address, sex, null, null));
                        }
                    }
                });
            }
        });
    }
}
