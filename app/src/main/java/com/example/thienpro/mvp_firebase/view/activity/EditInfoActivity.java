package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityEditinfoBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.EditInfoPresenterImpl;
import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class EditInfoActivity extends BaseActivity<ActivityEditinfoBinding> implements EditInfoView {
    private EditInfoPresenter presenter;
    private User user;

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
        getBinding().spProvince.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.province_arrays)));

        presenter.loadUser();
    }

    @Override
    public void onSaveClick() {
        String name = getBinding().etName.getText().toString();

        if (getBinding().etName.getText().toString().length() >= 30)
            Toast.makeText(this, R.string.ten_co_do_dai_duoi_30_ki_tu, Toast.LENGTH_SHORT).show();
        else {
            presenter.updateUser(name, getBinding().spProvince.getSelectedItem().toString(), getBinding().rbNam.isChecked());
        }
    }

    @Override
    public void getUser(User user) {
        this.user = user;
        getBinding().setData(user);
        int i = 0;
        for (String string : getResources().getStringArray(R.array.province_arrays)) {
            if (string.equals(user.getAddress())) {
                getBinding().spProvince.setSelection(i);
            }
            i++;
        }
        if (user.getSex())
            getBinding().rbNam.setChecked(true);
        else getBinding().rbNu.setChecked(true);
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
