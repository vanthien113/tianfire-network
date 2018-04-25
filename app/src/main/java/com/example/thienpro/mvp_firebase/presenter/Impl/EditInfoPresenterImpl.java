package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.example.thienpro.mvp_firebase.view.activity.EditInfoActivity;
import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class EditInfoPresenterImpl extends BasePresentermpl<EditInfoView> implements EditInfoPresenter {
    private UserInteractor userInteractor;
    private UserManager userManager;

    public EditInfoPresenterImpl(UserInteractor userInteractor, UserManager userManager) {
        this.userInteractor = userInteractor;
        this.userManager = userManager;
    }

    @Override
    public void updateUser(final String name, final String address, final boolean sex) {
        if (getView() == null)
            return;
        getView().showLoadingDialog();

        userInteractor.updateUser(name, address, sex, new UserInteractor.ExceptionCheckCallback() {
            @Override
            public void onFinish(Exception e) {
                if (getView() == null)
                    return;
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showChangeInfoComplete();
                }
            }
        });
    }

    @Override
    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == EditInfoActivity.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                getView().showAddress(PlaceAutocomplete.getPlace(context, data).getName().toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                getView().showAddress(PlaceAutocomplete.getStatus(context, data).getStatus().toString());
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }
}
