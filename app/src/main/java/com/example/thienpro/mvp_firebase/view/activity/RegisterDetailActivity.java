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
import com.example.thienpro.mvp_firebase.databinding.ActivityRegisterAddDetailBinding;
import com.example.thienpro.mvp_firebase.view.RegisterDetailView;

/**
 * Created by vanthien113 on 1/13/2018.
 */

public class RegisterDetailActivity extends AppCompatActivity implements RegisterDetailView {
    private ActivityRegisterAddDetailBinding binding;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, RegisterDetailActivity.class));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_add_detail);
        binding.setEvent(this);
        binding.sp.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.province_arrays)));
    }

    @Override
    public void onNextClick() {
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String name = binding.etName.getText().toString();

        if (name.isEmpty())
            Toast.makeText(this, "Không được để trống tên!", Toast.LENGTH_SHORT).show();
        else {
            if (name.length() >= 30)
                Toast.makeText(this, "Tên có độ dài dưới 30 ký tự!", Toast.LENGTH_SHORT).show();
            else {
                naviagationToRegister(name, binding.sp.getSelectedItem().toString(), binding.rbNam.isChecked());
            }
        }
    }

    @Override
    public void navigationToVerifiEmail() {

    }

    public void naviagationToRegister(String name, String address, boolean sex) {
        RegisterActivity.startActivity(this, name, address, sex);
    }
}
