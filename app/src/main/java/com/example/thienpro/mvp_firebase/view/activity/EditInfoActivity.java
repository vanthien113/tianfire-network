package com.example.thienpro.mvp_firebase.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityEditinfoBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.EditInfoPresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.EditInfoPresenterImpl;
import com.example.thienpro.mvp_firebase.view.EditInfoView;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class EditInfoActivity extends AppCompatActivity implements EditInfoView {
    private ActivityEditinfoBinding binding;
    private EditInfoPresenter editInfoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editinfo);
        editInfoPresenter = new EditInfoPresenterImpl(this);
        binding.setEvent(this);
        editInfoPresenter.loadUser();
    }

    @Override
    public void onSaveClick() {
        if (TextUtils.isEmpty(binding.etEmail.getText()) || TextUtils.isEmpty(binding.etName.getText())|| TextUtils.isEmpty(binding.etAddress.getText()))
            Toast.makeText(this, "Nhập thông tin cho các trường!", Toast.LENGTH_SHORT).show();
        else if(binding.etName.getText().toString().length()>= 30)
            Toast.makeText(this, "Tên có độ dài dưới 30 ký tự!", Toast.LENGTH_SHORT).show();
        else {
            editInfoPresenter.updateUser(binding.etEmail.getText().toString(), binding.etName.getText().toString(), binding.etAddress.getText().toString(), binding.rbEditnam.isChecked());
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getUser(User user) {
        binding.tvLoading.setVisibility(View.GONE);
        binding.setData(user);
        if (user.getSex())
            binding.rbEditnam.setChecked(true);
        else binding.rbEditnu.setChecked(true);
    }
}
