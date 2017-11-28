package com.example.thienpro.mvp_firebase.model.Impl;

import android.support.annotation.NonNull;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/*
*- Lớp M: xử lý dữ liệu -> Trả dữ liệu về P thông qua callback
* */

/**
 * Created by ThienPro on 11/10/2017.
 */

public class UserInteractorImpl implements UserInteractor {
    private LoadUserListener loadUserListener;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser users;

    public UserInteractorImpl(LoadUserListener loadUserListener) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        this.loadUserListener = loadUserListener;
        users = mAuth.getCurrentUser();
    }

    public boolean signedInCheck() {
        if (users != null) {
            return true;
        }
        return false;
    }

    public void register(final String email, String password, final String name, final String address, final boolean sex) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            users = mAuth.getCurrentUser();
                            User user = new User(email, name, address, sex);
                            mDatabase.child("users").child(users.getUid()).setValue(user); //setValue để thêm node
                            loadUserListener.navigationToHome();
                        } else loadUserListener.onRegisterFail();
                    }
                });
    }

    public void updateUser(String email, final String name, String address, Boolean sex) {
        String userId = users.getUid();
        User user = new User(email, name, address, sex);
        Map<String, Object> postValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userId, postValues);

        mDatabase.updateChildren(childUpdates);

        //Edit name in post
        mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String firstValue = (String) map.get("id");
                    String thirdValue = (String) map.get("timePost");
                    String foureValue = (String) map.get("post");

                    if (firstValue.equals(users.getUid().toString())) {
                        Map<String, Object> childUpdates1 = new HashMap<>();

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("id", firstValue);
                        result.put("name", name);
                        result.put("timePost", thirdValue);
                        result.put("post", foureValue);

                        childUpdates1.put("/posts/" + thirdValue, result);
                        mDatabase.updateChildren(childUpdates1);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void getUser() {
        users = mAuth.getCurrentUser();

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

                User user = new User(secondValue, thirdValue, firstValue, fourValue);
                loadUserListener.getUser(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.child("users").child(users.getUid()).addValueEventListener(valueEventListener);
    }

    public void sigIn(String email, String password) {
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadUserListener.navigationToHome();
                        } else {
                            loadUserListener.onLoginFail();
                        }
                    }
                });
    }
}
