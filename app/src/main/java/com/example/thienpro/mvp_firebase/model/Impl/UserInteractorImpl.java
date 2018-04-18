package com.example.thienpro.mvp_firebase.model.Impl;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.LogUltil;
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

import java.util.ArrayList;
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
    private static final String USERS = "users";
    private static final String POSTS = "posts";
    private static final String AVATARS = "avatars";

    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String SEX = "sex";
    private static final String AVATAR = "avatar";
    private static final String COVER = "cover";

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
    public void verifiEmail(final VerifiEmailCheckCallback callback) {
        if (users != null) {
            users.reload();
            if (users.isEmailVerified()) {
                callback.checker(null, null);
            } else if (!users.isEmailVerified()) {
                users.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                callback.checker(null, users.getEmail());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callback.checker(e, users.getEmail());
                            }
                        });
            }
        }
    }

    //Should Hard Avatar
    public void register(final String email, String password, final String name, final String address, final boolean sex, final RegisterCheckCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            users = mAuth.getCurrentUser();
                            User user = new User(users.getUid(), email, name, address, sex, null, null);
                            mDatabase.child(USERS).child(users.getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            callback.checker(null);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            callback.checker(e);
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.checker(e);
                    }
                });
    }

    @Override
    public void changePassword(String password, final ChangePasswordCallback callback) {
        users.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.changePasswordCallback(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.changePasswordCallback(e);
            }
        });
    }

    @Override
    public void forgotPassword(String email, final ChangePasswordCallback callback) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                callback.changePasswordCallback(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.changePasswordCallback(e);
            }
        });
    }

    @Override
    public void getFriendInfomation(String userId, final FriendInfomationCallback callback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.friendInfomation(null, user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.friendInfomation(databaseError, null);
            }
        };

        mDatabase.child(USERS).child(userId).addValueEventListener(valueEventListener);
    }

    @Override
    public void searchUser(final String userName, final SearchUserCallBack callBack) {
        final ArrayList<User> list = new ArrayList<>();

        mDatabase.child(USERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    User user = dsp.getValue(User.class);
                    if (user.getName().contains(userName)) {
                        list.add(user);
                    }
                }
                callBack.onFinish(null, list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFinish(databaseError, null);
            }
        });
    }

    public void updateUser(final String name, String address, Boolean sex, final UpdateUserCallback callback) {
        String userId = users.getUid();

        HashMap<String, Object> result = new HashMap<>();
        result.put(NAME, name);
        result.put(ADDRESS, address);
        result.put(SEX, sex);

        mDatabase.child(USERS).child(userId).updateChildren(result).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.updateUser(e);
            }
        });

        //Edit name in post
        mDatabase.child(POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    String id = (String) map.get("id");
                    String timePost = (String) map.get("timePost");

                    if (id.equals(users.getUid())) {
                        HashMap<String, Object> result = new HashMap<>();
                        result.put(NAME, name);

                        mDatabase.child(POSTS).child(timePost).updateChildren(result)
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        callback.updateUser(e);
                                    }
                                })
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        callback.updateUser(null);
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

    public void addAvatar(final Uri uri, final AddAvatarCallback callback) {
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

                                            //Edit avatar in post
                                            mDatabase.child(POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                                        @SuppressWarnings("unchecked")
                                                        Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                                                        String id = (String) map.get("id");
                                                        String timePost = (String) map.get("timePost");

                                                        if (id.equals(users.getUid().toString())) {
                                                            HashMap<String, Object> result = new HashMap<>();
                                                            result.put(AVATAR, uri.toString());

                                                            mDatabase.child(POSTS).child(timePost).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    //add in user
                                                                    String userId = users.getUid();

                                                                    HashMap<String, Object> result = new HashMap<>();
                                                                    result.put(AVATAR, uri.toString());

                                                                    mDatabase.child(USERS).child(userId).updateChildren(result)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    callback.addAvatar(null, uri.toString());

                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    callback.addAvatar(e, null);
                                                                                }
                                                                            });
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
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            callback.addAvatar(e, null);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.addAvatar(e, null);
                        }
                    });
        }
    }

    @Override
    public void addCover(final Uri coverUri, final AddCoverCallback callback) {
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

                                            HashMap<String, Object> result = new HashMap<>();
                                            result.put(COVER, uri.toString());

                                            mDatabase.child(USERS).child(userId).updateChildren(result)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            callback.addCover(null, uri.toString());
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            callback.addCover(e, null);
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            callback.addCover(e, null);
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.addCover(e, null);
                        }
                    });
        }
    }

    @Override
    public void signedInCheck(LoggedInCheckCallback loginCheck) {
        users = FirebaseAuth.getInstance().getCurrentUser();
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
    public void getUser(final GetUserCallback callback, boolean loadUser) {
        users = FirebaseAuth.getInstance().getCurrentUser();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.getUser(null, user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.getUser(databaseError, null);
            }
        };

        mDatabase.child(USERS).child(users.getUid()).addValueEventListener(valueEventListener);
    }

    public void sigIn(String email, String password, final LoginCheckCallback callback) {
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            callback.checker(true, null);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.checker(false, e);
                    }
                });
    }

    @Override
    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
