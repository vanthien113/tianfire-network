package com.example.thienpro.mvp_firebase.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityEditinfoBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.RegisterPresenter;
import com.example.thienpro.mvp_firebase.presenter.impl.RegisterPresenterImpl;
import com.example.thienpro.mvp_firebase.view.EditInfoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class EditInfoActivity extends AppCompatActivity implements EditInfoView {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ActivityEditinfoBinding binding;
    private RegisterPresenter registerPresenter;
    private FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editinfo);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        binding.setEvent(this);
        registerPresenter = new RegisterPresenterImpl(mDatabase);
        user = mAuth.getCurrentUser();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String firstValue = (String) map.get("address");
                String secondValue = (String) map.get("email");
                String thirdValue = (String) map.get("name");
                Boolean fourValue = (Boolean) map.get("sex");

                if (fourValue == false)
                    binding.rbEditnu.setChecked(true);

                User user = new User(secondValue, thirdValue, firstValue, fourValue);
                binding.setData(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };

        mDatabase.child("users").child(user.getUid()).addValueEventListener(valueEventListener);
    }

    @Override
    public void onSaveClick() {
        Log.e("THIEN", user.getUid());
        Log.e("THIEN", binding.etEditun.getText().toString());
        Log.e("THIEN", binding.etEditname.getText().toString());
        Log.e("THIEN", binding.etEditadd.getText().toString());
        try {
            registerPresenter.updateUser(user.getUid().toString(), binding.etEditun.getText().toString(),
                    binding.etEditname.getText().toString(), binding.etEditadd.getText().toString(),
                    binding.rbEditnam.isChecked());
            Toast.makeText(this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Lưu không thành công!", Toast.LENGTH_SHORT).show();
        }
    }
}
