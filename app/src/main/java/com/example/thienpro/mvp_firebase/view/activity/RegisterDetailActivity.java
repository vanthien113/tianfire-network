package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityRegisterAddDetailBinding;
import com.example.thienpro.mvp_firebase.view.RegisterDetailView;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

/**
 * Created by vanthien113 on 1/13/2018.
 */

public class RegisterDetailActivity extends BaseActivity<ActivityRegisterAddDetailBinding> implements RegisterDetailView {
    public static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, RegisterDetailActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_add_detail;
    }

    @Override
    protected void init() {
        getBinding().setEvent(this);
    }

    @Override
    public void onNextClick() {
        String name = getBinding().etName.getText().toString();

        if (validate(name)) {
            naviagationToRegister(name, getBinding().tvAddress.getText().toString(), getBinding().rbNam.isChecked());
        }
    }

    @Override
    public void onAddressClick() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
        } catch (GooglePlayServicesNotAvailableException e) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EditInfoActivity.PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
            getBinding().tvAddress.setText(PlaceAutocomplete.getPlace(this, data).getName().toString());
        }
    }

    private boolean validate(String name) {
        if (TextUtils.isEmpty(name)) {
            showToastMessage(R.string.vui_long_nhap_ten);
            return false;
        } else if (name.length() >= 30) {
            showToastMessage(R.string.ten_co_do_dai_duoi_30_ki_tu);
            return false;
        }
        return true;
    }

    public void naviagationToRegister(String name, String address, boolean sex) {
        RegisterActivity.startActivity(this, name, address, sex);
    }

    @Override
    protected void startScreen() {

    }

    @Override
    protected void resumeScreen() {

    }

    @Override
    protected void pauseScreen() {

    }

    @Override
    protected void destroyScreen() {

    }
}
