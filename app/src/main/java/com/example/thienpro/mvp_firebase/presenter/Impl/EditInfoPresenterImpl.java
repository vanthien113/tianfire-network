package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Intent;

import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

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
        getView().showLoadingDialog();

        userInteractor.updateUser(name, address, sex, new UserInteractor.UpdateUserCallback() {
            @Override
            public void updateUser(Exception e) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showMessenger("Cập nhật thành công!");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == EditInfoActivity.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                getView().showAddress(PlaceAutocomplete.getPlace(context, data).getName().toString());
//            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
//                address = PlaceAutocomplete.getStatus(context, data).getStatus().toString();
//                getBinding().spProvince.setText(address);
//            } else if (resultCode == RESULT_CANCELED) {
//            }
//        }
    }
}
