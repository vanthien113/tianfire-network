package com.example.thienpro.mvp_firebase.model.Impl;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 *- Lớp M: xử lý dữ liệu -> Trả dữ liệu về P thông qua callback
 * */

/**
 * Created by ThienPro on 11/10/2017.
 */

public class UserInteractorImpl implements UserInteractor {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser users;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference ref;

    public UserInteractorImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        users = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    public void verifiEmail(final VerifiEmailCheck verifiEmailCheck) {
        if (users != null) {
            users.reload();
            if (users.isEmailVerified()) {
                verifiEmailCheck.checker(null, null);
            } else if (!users.isEmailVerified()) {
                users.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                verifiEmailCheck.checker(null, users.getEmail());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                verifiEmailCheck.checker(e, users.getEmail());
                            }
                        });
            }
        }
    }

    //Should Hard Avatar
    public void register(final String email, String password, final String name, final String address, final boolean sex, final RegisterCheck registerCheck) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            users = mAuth.getCurrentUser();
                            User user = new User(email, name, address, sex, null);
                            mDatabase.child("users").child(users.getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            registerCheck.checker(null);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            registerCheck.checker(e);
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        registerCheck.checker(e);
                    }
                });
    }

//    private void uploadImage(final String email, final String password, final String name, final String address, final boolean sex, Uri filePath) {
//        if (filePath != null) {
//            ref = storageReference.child("avatars/" + UUID.randomUUID().toString());
//            ref.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            getUrlImage(email, password, name, address, sex);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });
//        } else {
//            createUser(email, password, name, address, sex, "null");
//        }
//    }
//
//    private void createUser(final String email, String password, final String name, final String address, final boolean sex, final String avatar) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            users = mAuth.getCurrentUser();
//                            User user = new User(email, name, address, sex, avatar);
//                            mDatabase.child("users").child(users.getUid()).setValue(user); //setValue để thêm node
//                            userListener.navigationToVerifiEmail();
//                        } else userListener.onRegisterFail();
//                    }
//                });
//    }

//    private void getUrlImage(final String email, final String password, final String name, final String address, final boolean sex) {
//        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                createUser(email, password, name, address, sex, uri.toString());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//                Log.e("THIEN", "GET NOT OKE");
//            }
//        });
//    }

    public void updateUser(String email, final String name, String address, Boolean sex, final UpdateUserListener updateUserListener) {
        String userId = users.getUid();
        User user = new User(email, name, address, sex, null);
        Map<String, Object> postValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userId, postValues);

        mDatabase.updateChildren(childUpdates).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                updateUserListener.updateUser(e);
            }
        });

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
                        mDatabase.updateChildren(childUpdates1).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                updateUserListener.updateUser(e);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void addAvatar(final String email, final String name, final String address, final Boolean sex, final Uri uri, final AddAvatarListener addAvatarListener) {
        //Up im
        if (uri != null) {
            ref = storageReference.child("avatars/" + UUID.randomUUID().toString());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(final Uri uri) {
                                            //add in usr
                                            String userId = users.getUid();
                                            User user = new User(email, name, address, sex, uri.toString());
                                            Map<String, Object> postValues = user.toMap();
                                            Map<String, Object> childUpdates = new HashMap<>();
                                            childUpdates.put("/users/" + userId, postValues);

                                            mDatabase.updateChildren(childUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            addAvatarListener.addAvatar(null);
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            addAvatarListener.addAvatar(e);
                                                        }
                                                    });

                                            //Edit avatar in post
                                            mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                                        @SuppressWarnings("unchecked")
                                                        Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                                                        String firstValue = (String) map.get("id");
                                                        String thirdValue = (String) map.get("timePost");
                                                        String foureValue = (String) map.get("post");
                                                        String fiveValue = (String) map.get("image");

                                                        if (firstValue.equals(users.getUid().toString())) {
                                                            Map<String, Object> childUpdates1 = new HashMap<>();

                                                            HashMap<String, Object> result = new HashMap<>();
                                                            result.put("id", firstValue);
                                                            result.put("name", name);
                                                            result.put("timePost", thirdValue);
                                                            result.put("post", foureValue);
                                                            result.put("image", fiveValue);
                                                            result.put("avatar", uri.toString());

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
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            addAvatarListener.addAvatar(e);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            addAvatarListener.addAvatar(e);
                        }
                    });
        }
    }

    @Override
    public void signedInCheck(LoginCheck loginCheck) {
        if (users != null) {
            users.reload();
            if (users.isEmailVerified()) {
                loginCheck.checker(true);
            } else if (!users.isEmailVerified()) {
                loginCheck.checker(false);
            }
        }
    }

    @Override
    public void getUser(final GetUserListener listener) {
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
                String fivevalue = (String) map.get("avatar");

                User user = new User(secondValue, thirdValue, firstValue, fourValue, fivevalue);
                listener.getUser(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.child("users").child(users.getUid()).addValueEventListener(valueEventListener);
    }

    ///Fixed///
    public void sigIn(String email, String password, final LoginCheck loginCheck) {
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginCheck.checker(true);
                        } else {
                            loginCheck.checker(false);
                        }
                    }
                });
    }

    @Override
    public void logOut(LogoutCheck logoutCheck) {
        FirebaseAuth.getInstance().signOut();
        logoutCheck.checker(true);
    }
}
