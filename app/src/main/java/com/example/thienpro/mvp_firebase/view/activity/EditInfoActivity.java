package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityEditinfoBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.EditInfoPresenterImpl;
import com.example.thienpro.mvp_firebase.ultils.LoadingDialog;
import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.google.firebase.database.DatabaseError;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class EditInfoActivity extends AppCompatActivity implements EditInfoView {
    private ActivityEditinfoBinding binding;
    private EditInfoPresenter presenter;
    private LoadingDialog loadingDialog;
    private User user;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, EditInfoActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editinfo);

        presenter = new EditInfoPresenterImpl(this, this);
        loadingDialog = new LoadingDialog(this);

        binding.setEvent(this);
        binding.spProvince.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.province_arrays)));

        presenter.loadUser();
    }

    @Override
    public void onSaveClick() {
        String email = binding.etEmail.getText().toString();
        String name = binding.etName.getText().toString();

        if (email.isEmpty() || name.isEmpty())
            Toast.makeText(this, R.string.nhap_thong_tin_cho_cac_truong, Toast.LENGTH_SHORT).show();
        else if (binding.etName.getText().toString().length() >= 30)
            Toast.makeText(this, R.string.ten_co_do_dai_duoi_30_ki_tu, Toast.LENGTH_SHORT).show();
        else {
            presenter.updateUser(email, name, binding.spProvince.getSelectedItem().toString(), binding.rbNam.isChecked(), user.getAvatar(), user.getCover());
            Toast.makeText(this, R.string.cap_nhat_thanh_cong, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getUser(User user) {
        this.user = user;
        binding.setData(user);
        int i = 0;
        for (String string : getResources().getStringArray(R.array.province_arrays)) {
            if (string.equals(user.getAddress())) {
                binding.spProvince.setSelection(i);
            }
            i++;
        }
        if (user.getSex())
            binding.rbNam.setChecked(true);
        else binding.rbNu.setChecked(true);
    }

    @Override
    public void getUserError(DatabaseError e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        loadingDialog.show();
    }

    @Override
    public void hideDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void getUserError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateSuccess() {
        Toast.makeText(this, R.string.cap_nhat_thanh_cong, Toast.LENGTH_SHORT).show();
    }
}
