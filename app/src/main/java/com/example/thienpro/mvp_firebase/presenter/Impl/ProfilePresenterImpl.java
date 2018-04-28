package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.esafirm.imagepicker.model.Image;
import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.manager.PostManager;
import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.Impl.BaseInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.fragment.ProfileFragment;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ThienPro on 11/16/2017.
 */

public class ProfilePresenterImpl extends BasePresentermpl<ProfileView> implements ProfilePresenter {
    private PostInteractor postInteractor;
    private UserInteractor userInteractor;
    private UserManager userManager;
    private PostManager postManager;
    private UserManager.OnUserChangeListener onUserChangeListener = new UserManager.OnUserChangeListener() {
        @Override
        public void onChange(User newUser) {
            getView().onUserUpdated();
        }
    };
    private PostManager.OnPostChangeListener onPostChangeListener = new PostManager.OnPostChangeListener() {
        @Override
        public void onChange() {
            getView().onUserUpdated();
        }
    };

    public ProfilePresenterImpl(UserManager userManager, PostInteractor postInteractor, UserInteractor userInteractor, PostManager postManager) {
        this.postInteractor = postInteractor;
        this.userInteractor = userInteractor;
        this.userManager = userManager;
        this.postManager = postManager;
    }

    @Override
    public void loadPost() {
        getView().showLoading();
        postInteractor.loadPersonalPost(new PostInteractor.ListPostCallback() {
            @Override
            public void onFinish(DatabaseError e, ArrayList<Post> listPost) {
                getView().hideLoading();

                if (e == null) {
                    getView().showListPost(listPost);
                } else {
                    getView().showDatabaseError(e);
                }
            }
        });
    }

    @Override
    public void getUser() {
        if (getView() == null)
            return;
        getView().showLoadingDialog();
        userInteractor.getUser(new UserInteractor.UserCallback() {
            @Override
            public void onFinish(DatabaseError error, User user) {
                if (getView() == null)
                    return;
                getView().hideLoadingDialog();
                if (error != null) {
                    getView().showDatabaseError(error);
                } else {
                    userManager.updateCurrentUser(user);
                    postManager.postChange();
                }
            }
        }, true);
    }

    @Override
    public void deletePost(Post post) {
        if (getView() == null)
            return;
        getView().showLoadingDialog();
        postInteractor.deletePost(post, new PostInteractor.ExceptionCallback() {
            @Override
            public void onFinish(Exception e) {
                if (getView() == null)
                    return;
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showDeleteComplete();
                    postManager.postChange();
                }
            }
        });
    }

    @Override
    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == ProfileFragment.REQUEST_CHANGE_AVATAR && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                uploadAvatarImage(SHBitmapHelper.getUriAndCompressBitmap(context, image.getPath()));
            }
            return;
        }
        if (requestCode == ProfileFragment.REQUEST_CHANGE_COVER && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                uploadCoverImage(SHBitmapHelper.getUriAndCompressBitmap(context, image.getPath()));
            }
        }
    }

    private void uploadAvatarImage(final Uri uri) {
        if (getView() == null)
            return;
        getView().showLoading();

        userInteractor.uploadImage(uri, BaseInteractorImpl.AVATARS, new PostInteractor.GetStringCallback() {
            @Override
            public void onFinish(Exception e, String string) {
                addAvatar(string);
            }
        });
    }

    private void addAvatar(final String avatarUrl) {
        userInteractor.addAvatar(avatarUrl, new UserInteractor.ExceptionCheckCallback() {
            @Override
            public void onFinish(Exception e) {
                if (getView() == null)
                    return;
                getView().hideLoading();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    postManager.postChange();
                    userManager.updateAvatar(avatarUrl);
                    getView().showChangeComplete();
                }
            }
        });
    }

    private void uploadCoverImage(final Uri uri) {
        if (getView() == null)
            return;
        getView().showLoading();

        userInteractor.uploadImage(uri, BaseInteractorImpl.COVER, new PostInteractor.GetStringCallback() {
            @Override
            public void onFinish(Exception e, String string) {
                addCover(string);
            }
        });
    }

    private void addCover(final String coverUrl) {
        userInteractor.addCover(coverUrl, new UserInteractor.ExceptionCheckCallback() {
            @Override
            public void onFinish(Exception e) {
                if (getView() == null)
                    return;
                getView().hideLoading();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    postManager.postChange();
                    userManager.updateCover(coverUrl);
                    getView().showChangeComplete();
                }
            }
        });
    }

    @Override
    public void attachView(ProfileView view) {
        userManager.addOnUserChangeListener(onUserChangeListener);
        postManager.addOnPostChangeListener(onPostChangeListener);
        super.attachView(view);
    }

    @Override
    public void detach() {
        userManager.removeUserChangeListener(onUserChangeListener);
        postManager.removePostChangeListener(onPostChangeListener);
        super.detach();
    }
}
