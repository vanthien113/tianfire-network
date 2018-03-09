package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityRegisterAddDetailBinding;
import com.example.thienpro.mvp_firebase.view.RegisterDetailView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

/**
 * Created by vanthien113 on 1/13/2018.
 */

public class RegisterDetailActivity extends BaseActivity<ActivityRegisterAddDetailBinding> implements RegisterDetailView {
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, RegisterDetailActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_add_detail;
    }

    @Override
    protected void init() {
        viewDataBinding.setEvent(this);
        viewDataBinding.sp.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.province_arrays)));
    }

    @Override
    public void onNextClick() {
        String name = viewDataBinding.etName.getText().toString();

        if (name.isEmpty())
            Toast.makeText(this, R.string.khong_duoc_de_trong_ten, Toast.LENGTH_SHORT).show();
        else {
            if (name.length() >= 30)
                Toast.makeText(this, R.string.ten_co_do_dai_duoi_30_ki_tu, Toast.LENGTH_SHORT).show();
            else {
                naviagationToRegister(name, viewDataBinding.sp.getSelectedItem().toString(), viewDataBinding.rbNam.isChecked());
            }
        }
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
