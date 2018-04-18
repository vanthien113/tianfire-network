package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.esafirm.imagepicker.model.Image;
import com.example.thienpro.mvp_firebase.manager.PostManager;
import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.view.fragment.ProfileFragment;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * - Presenter: Là lớp xử lý logic từ dữ liệu nhận được.
 * - Nhận dữ liệu từ lớp Model
 * - Đẩy dữ liệu lên lớp V.
 */

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
        postInteractor.loadPersonalPost(new PostInteractor.LoadPersonalPostCallback() {
            @Override
            public void post(DatabaseError e, ArrayList<Post> listPost) {
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
        userInteractor.getUser(new UserInteractor.GetUserCallback() {
            @Override
            public void getUser(DatabaseError error, User user) {
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
        postInteractor.deletePost(post, new PostInteractor.DeletePostCallback() {
            @Override
            public void listPost(Exception e) {
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

                changeAvatar(SHBitmapHelper.getUriAndCompressBitmap(context, image.getPath()));
            }
            return;
        }
        if (requestCode == ProfileFragment.REQUEST_CHANGE_COVER && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                changeCover(SHBitmapHelper.getUriAndCompressBitmap(context, image.getPath()));
            }
        }
    }

    private void changeAvatar(final Uri uri) {
        if (getView() == null)
            return;
        getView().showLoading();
        userInteractor.addAvatar(uri, new UserInteractor.AddAvatarCallback() {
            @Override
            public void addAvatar(Exception e, String uri) {
                if (getView() == null)
                    return;
                getView().hideLoading();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showChangeComplete();
                }
            }
        });
    }

    private void changeCover(final Uri uri) {
        if (getView() == null)
            return;
        getView().showLoading();
        userInteractor.addCover(uri, new UserInteractor.AddCoverCallback() {
            @Override
            public void addCover(Exception e, String uri) {
                if (getView() == null)
                    return;
                getView().hideLoading();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showChangeComplete();
                }
            }
        });
    }

    @Override
    public void attachView(ProfileView view) {
        super.attachView(view);
        userManager.addOnUserChangeListener(onUserChangeListener);
        postManager.addOnPostChangeListener(onPostChangeListener);
    }

    @Override
    public void detach() {
        super.detach();
        userManager.removeUserChangeListener(onUserChangeListener);
        postManager.removePostChangeListener(onPostChangeListener);
    }
}
