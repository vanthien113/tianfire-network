package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.example.thienpro.mvp_firebase.view.activity.EditInfoActivity;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class EditInfoPresenterImpl extends BasePresentermpl<EditInfoView> implements EditInfoPresenter {
    private UserInteractor userInteractor;
    private Context context;

    public EditInfoPresenterImpl(Context context) {
        this.context = context;
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
                            userInteractor.saveCurrentLocalUser(new User(null, null, name, address, sex, null, null));
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EditInfoActivity.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                getView().showAddress(PlaceAutocomplete.getPlace(context, data).getName().toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                address = PlaceAutocomplete.getStatus(context, data).getStatus().toString();
//                getBinding().spProvince.setText(address);
//            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }
}
