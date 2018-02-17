package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
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

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, EditInfoActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editinfo);
        editInfoPresenter = new EditInfoPresenterImpl(this);
        binding.setEvent(this);
        binding.spProvince.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.province_arrays)));
        editInfoPresenter.loadUser();
    }

    @Override
    public void onSaveClick() {
        String email = binding.etEmail.getText().toString();
        String name = binding.etName.getText().toString();

        if (email.isEmpty() || name.isEmpty())
            Toast.makeText(this, "Nhập thông tin cho các trường!", Toast.LENGTH_SHORT).show();
        else if (binding.etName.getText().toString().length() >= 30)
            Toast.makeText(this, "Tên có độ dài dưới 30 ký tự!", Toast.LENGTH_SHORT).show();
        else {
            editInfoPresenter.updateUser(email, name, binding.spProvince.getSelectedItem().toString(), binding.rbNam.isChecked());
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getUser(User user) {
        binding.pbLoading.setVisibility(View.GONE);
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
    public void getuser(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
