package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityEditinfoBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.EditInfoPresenterImpl;
import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class EditInfoActivity extends BaseActivity<ActivityEditinfoBinding> implements EditInfoView {
    public static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private EditInfoPresenter presenter;
    private User user;
    private String address;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, EditInfoActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editinfo;
    }

    @Override
    protected void init() {
        presenter = new EditInfoPresenterImpl(this);
        presenter.attachView(this);

        getBinding().setEvent(this);
        presenter.loadUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveClick() {
        String name = getBinding().etName.getText().toString();
        if (validate(name)) {
            presenter.updateUser(name, address, getBinding().rbNam.isChecked());
        }
    }

    private boolean validate(String name) {
        if (getBinding().etName.getText().toString().length() >= 30) {
            Toast.makeText(this, R.string.ten_co_do_dai_duoi_30_ki_tu, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void getUser(User user) {
        this.user = user;

        getBinding().setData(user);

        if (user.getSex())
            getBinding().rbNam.setChecked(true);
        else getBinding().rbNu.setChecked(true);
    }

    @Override
    public void onAddressClick() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(EditInfoActivity.this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
        } catch (GooglePlayServicesNotAvailableException e) {
        }
    }

    @Override
    public void showAddress(String address) {
        this.address = address;
        getBinding().spProvince.setText(address);
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
