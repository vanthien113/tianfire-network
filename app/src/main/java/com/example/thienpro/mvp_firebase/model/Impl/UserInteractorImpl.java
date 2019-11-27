package com.example.thienpro.mvp_firebase.model.Impl;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class UserInteractorImpl extends BaseInteractorImpl implements UserInteractor {
    private DatabaseReference mDatabase;
    private FirebaseUser users;

    public UserInteractorImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void verifiEmail(StringCallback callback) {
        users = FirebaseAuth.getInstance().getCurrentUser();
        if (users == null) {
            return;
        }

        users.reload().addOnSuccessListener(aVoid -> {
            if (users.isEmailVerified()) {
                callback.onFinish(null, null);
            } else {
                //send email
                users.sendEmailVerification()
                        .addOnSuccessListener(task -> callback.onFinish(null, users.getEmail()))
                        .addOnFailureListener(e -> callback.onFinish(e, users.getEmail()));
            }
        });
    }

    public void register(String email, String password, String name, String address, boolean sex, ExceptionCheckCallback callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(task ->
                        mDatabase.child(USERS).child(task.getUser().getUid())
                                .setValue(new User(task.getUser().getUid(), email, name, address, sex, null, null))
                                .addOnSuccessListener(task1 -> callback.onFinish(null))
                                .addOnFailureListener(e -> callback.onFinish(e)))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    @Override
    public void changePassword(String password, ExceptionCheckCallback callback) {
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(password)
                .addOnSuccessListener(task -> callback.onFinish(null))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    @Override
    public void forgotPassword(String email, ExceptionCheckCallback callback) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener(task -> callback.onFinish(null))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    @Override
    public void getFriendInfomation(String userId, UserCallback callback) {
        mDatabase.child(USERS).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onFinish(null, dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFinish(databaseError, null);
            }
        });
    }

    @Override
    public void getAllUser(UsersCallBack callBack) {
        mDatabase.child(USERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> list = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    list.add(dsp.getValue(User.class));
                }
                callBack.onFinish(null, list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFinish(databaseError, null);
            }
        });
    }

    public void updateUser(String name, String address, Boolean sex, ExceptionCheckCallback callback) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(NAME, name);
        result.put(ADDRESS, address);
        result.put(SEX, sex);

        mDatabase.child(USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(result)
                .addOnSuccessListener(aVoid -> updateUseInfoInPost(name, callback))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    private void updateUseInfoInPost(String name, ExceptionCheckCallback callback) {
        mDatabase.child(POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String id = (String) map.get(ID);
                    String timePost = (String) map.get(TIMEPOST);

                    if (id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        HashMap<String, Object> result = new HashMap<>();
                        result.put(NAME, name);

                        mDatabase.child(POSTS).child(timePost)
                                .updateChildren(result)
                                .addOnFailureListener(e -> callback.onFinish(e));
                    }
                }
                callback.onFinish(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void addAvatar(String avatarUrl, ExceptionCheckCallback callback) {
        editAvatarInUser(avatarUrl, callback);
    }

    @Override
    public void addCover(String coverUrl, ExceptionCheckCallback callback) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(COVER, coverUrl);

        mDatabase.child(USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(result)
                .addOnSuccessListener(task -> callback.onFinish(null))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    private void editAvatarInPost(String avatarUrl, ExceptionCheckCallback callback) {
        mDatabase.child(POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String id = (String) map.get(ID);
                    String timePost = (String) map.get(TIMEPOST);

                    if (id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        HashMap<String, Object> result = new HashMap<>();
                        result.put(AVATAR, avatarUrl);

                        mDatabase.child(POSTS).child(timePost).updateChildren(result)
                                .addOnSuccessListener(aVoid -> callback.onFinish(null))
                                .addOnFailureListener(e -> callback.onFinish(e));
                    }
                }
                callback.onFinish(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    private void editAvatarInUser(String avatarUrl, ExceptionCheckCallback callback) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(AVATAR, avatarUrl);

        mDatabase.child(USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(result)
                .addOnSuccessListener(task -> editAvatarInPost(avatarUrl, callback))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    @Override
    public void signedInCheck(BooleanCheckCallback loginCheck) {
        users = FirebaseAuth.getInstance().getCurrentUser();
        if (users != null) {
            users.reload().addOnSuccessListener(aVoid -> {
                if (users.isEmailVerified()) {
                    loginCheck.onFinish(true);
                } else if (!users.isEmailVerified()) {
                    loginCheck.onFinish(false);
                }
            });
        }
    }

    @Override
    public void getUser(UserCallback callback, boolean loadUser) {
        mDatabase.child(USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        callback.onFinish(null, dataSnapshot.getValue(User.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFinish(databaseError, null);
                    }
                });
    }

    public void sigIn(String email, String password, ExceptionCheckCallback callback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(task -> callback.onFinish(null))
                .addOnFailureListener(e -> callback.onFinish(e));
    }

    @Override
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
