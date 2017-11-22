package com.example.thienpro.mvp_firebase.model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.activity.LoginActivity;
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
import java.util.concurrent.Executor;

/*
*- Lớp M: xử lý dữ liệu -> Trả dữ liệu về P thông qua callback
* */

/**
 * Created by ThienPro on 11/10/2017.
 */

public class UserInteractor {
    private LoadUserListener loadUserListener;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser users;
    private Context context;

    public UserInteractor(LoadUserListener loadUserListener, Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        users = mAuth.getCurrentUser();
        this.loadUserListener = loadUserListener;
        this.context = context;
    }

    public void register(final String email, String password, String repassword, final String name, final String address, final boolean sex) {
        if (password.equals(repassword))
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(email, name, address, sex);
                                mDatabase.child("users").child(users.getUid()).setValue(user); //setValue để thêm node
                                loadUserListener.navigationToHome();
                            }
                            else loadUserListener.onRegisterFail();

                        }
                    });
    }

    public void updateUser(String email, String name, String address, Boolean sex) {
        String userId = users.getUid();
        User user = new User(email, name, address, sex);
        Map<String, Object> postValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userId, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    public void getUser() {
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
    public void Sigin(String email, String password) {
        mAuth = FirebaseAuth.getInstance();

        if(email.equals("") || password.equals(""))
            loadUserListener.onSignInNull();
        else
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
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
