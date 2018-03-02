package com.example.thienpro.mvp_firebase.model.Impl;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.SharedPreferencesUtil;
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
    private SharedPreferencesUtil currentUser;

    public UserInteractorImpl(Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        users = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        currentUser = new SharedPreferencesUtil(context);
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
                            User user = new User(email, name, address, sex, null, null);
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

    @Override
    public void loadCurrentLocalUser(LoadCurrentLocalUserListener loadCurrentLocalUserListeners) {
        loadCurrentLocalUserListeners.currentLocalUser(currentUser.getUser());
    }

    @Override
    public void saveCurrentLocalUser(User user) {
        currentUser.setUser(user);
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

    public void updateUser(String email, final String name, String address, Boolean sex, String avatar, String cover, final UpdateUserListener updateUserListener) {
        String userId = users.getUid();
        User user = new User(email, name, address, sex, avatar, cover);
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
                    String id = (String) map.get("id");
                    String timePost = (String) map.get("timePost");
                    String post = (String) map.get("post");
                    String image = (String) map.get("image");
                    String avatar = (String) map.get("avatar");

                    if (id.equals(users.getUid())) {
                        Map<String, Object> childUpdates1 = new HashMap<>();

                        HashMap<String, Object> result = new HashMap<>();
                        result.put("id", id);
                        result.put("name", name);
                        result.put("timePost", timePost);
                        result.put("post", post);
                        result.put("image", image);
                        result.put("avatar", avatar);

                        childUpdates1.put("/posts/" + timePost, result);
                        mDatabase.updateChildren(childUpdates1)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        updateUserListener.updateUser(e);
                                    }
                                })
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        updateUserListener.updateUser(null);
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

    public void addAvatar(final String email, final String name, final String address, final Boolean sex, final Uri uri, final String coverUri, final AddAvatarListener addAvatarListener) {
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
                                            User user = new User(email, name, address, sex, uri.toString(), coverUri);
                                            Map<String, Object> postValues = user.toMap();
                                            Map<String, Object> childUpdates = new HashMap<>();
                                            childUpdates.put("/users/" + userId, postValues);

                                            mDatabase.updateChildren(childUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            //Edit avatar in post
                                                            mDatabase.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                                                        @SuppressWarnings("unchecked")
                                                                        Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                                                                        String id = (String) map.get("id");
                                                                        String timePost = (String) map.get("timePost");
                                                                        String post = (String) map.get("post");
                                                                        String image = (String) map.get("image");

                                                                        if (id.equals(users.getUid().toString())) {
                                                                            Map<String, Object> childUpdates1 = new HashMap<>();

                                                                            HashMap<String, Object> result = new HashMap<>();
                                                                            result.put("id", id);
                                                                            result.put("name", name);
                                                                            result.put("timePost", timePost);
                                                                            result.put("post", post);
                                                                            result.put("image", image);
                                                                            result.put("avatar", uri.toString());

                                                                            childUpdates1.put("/posts/" + timePost, result);
                                                                            mDatabase.updateChildren(childUpdates1);
                                                                        }
                                                                    }
                                                                    addAvatarListener.addAvatar(null, uri.toString());

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
                                                            addAvatarListener.addAvatar(e, null);
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            addAvatarListener.addAvatar(e, null);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            addAvatarListener.addAvatar(e, null);
                        }
                    });
        }
    }

    @Override
    public void addCover(final String email, final String name, final String address, final Boolean sex, final String avatar, final Uri coverUri, final AddCoverListener addCoverListener) {
        //Up im

        if (coverUri != null) {
            ref = storageReference.child("covers/" + UUID.randomUUID().toString());
            ref.putFile(coverUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(final Uri uri) {
                                            //add in usr
                                            String userId = users.getUid();
                                            User user = new User(email, name, address, sex, avatar, uri.toString());
                                            Map<String, Object> postValues = user.toMap();
                                            Map<String, Object> childUpdates = new HashMap<>();
                                            childUpdates.put("/users/" + userId, postValues);

                                            mDatabase.updateChildren(childUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            addCoverListener.addCover(null, uri.toString());
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            addCoverListener.addCover(e, null);
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            addCoverListener.addCover(e, null);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            addCoverListener.addCover(e, null);
                        }
                    });
        }
    }

    @Override
    public void signedInCheck(LoggedInCheck loginCheck) {
        if (users != null) {
            users.reload();
            if (users.isEmailVerified()) {
                loginCheck.checker(1);
            } else if (!users.isEmailVerified()) {
                loginCheck.checker(2);
            }
        }
        loginCheck.checker(0);
    }

    @Override
    public void getUser(final GetUserListener listener, boolean loadUser) {
        users = mAuth.getCurrentUser();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String address = (String) map.get("address");
                String email = (String) map.get("email");
                String name = (String) map.get("name");
                Boolean sex = (Boolean) map.get("sex");
                String avatar = (String) map.get("avatar");
                String cover = (String) map.get("cover");

                User user = new User(email, name, address, sex, avatar, cover);

                currentUser.setUser(user);

                listener.getUser(null, user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.getUser(databaseError, null);
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
                            loginCheck.checker(true, null);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginCheck.checker(false, e);
                    }
                });
    }

    @Override
    public void logOut(LogoutCheck logoutCheck) {
        FirebaseAuth.getInstance().signOut();
        logoutCheck.checker(true);
    }
}
