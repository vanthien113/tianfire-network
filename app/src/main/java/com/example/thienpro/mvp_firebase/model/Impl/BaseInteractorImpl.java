package com.example.thienpro.mvp_firebase.model.Impl;

import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.BaseInteractor;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class BaseInteractorImpl implements BaseInteractor {
    public static final String LOCATIONS = "locations";
    public static final String POSTS = "posts";
    public static final String USERS = "users";
    public static final String IMAGES = "images";
    public static final String AVATARS = "avatars";
    public static final String COVER = "cover";
    public static final String COMMENTS = "comments";

    protected static final String ID = "id";
    protected static final String USERID = "userId";
    protected static final String NAME = "name";
    protected static final String POST = "post";
    protected static final String IMAGE = "image";
    protected static final String AVATAR = "avatar";
    protected static final String TIMEPOST = "timePost";
    protected static final String EMAIL = "email";
    protected static final String ADDRESS = "address";
    protected static final String SEX = "sex";

    protected StorageReference ref;

    public BaseInteractorImpl() {
    }

    public void uploadImage(Uri uri, String child, String userId, final PostInteractor.GetStringCallback callback) {
        ref = FirebaseStorage.getInstance().getReference().child(child).child(userId).child(UUID.randomUUID().toString());
        ref.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> getDownloadImageUrl(callback))
                .addOnFailureListener(e -> callback.onFinish(e, null));
    }

    private void getDownloadImageUrl(final PostInteractor.GetStringCallback callback) {
        ref.getDownloadUrl()
                .addOnSuccessListener(uri -> callback.onFinish(null, uri.toString()))
                .addOnFailureListener(exception -> callback.onFinish(exception, null));
    }
}
