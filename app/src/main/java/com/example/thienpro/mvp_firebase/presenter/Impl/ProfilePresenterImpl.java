package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.net.Uri;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.view.ProfileView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

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

    public ProfilePresenterImpl(Context context) {
        postInteractor = new PostInteractorImpl();
        userInteractor = new UserInteractorImpl(context);
    }

    public void loadPost() {
        getView().showLoadingDialog();

        postInteractor.loadPersonalPost(new PostInteractor.ListPostCallback() {
            @Override
            public void listPost(DatabaseError e, ArrayList<Post> listPost) {
                getView().hideLoadingDialog();

                if (e == null) {
                    getView().showList(listPost);
                } else {
                    getView().showDatabaseError(e);
                }
            }
        });
    }

    @Override
    public void getUser() {
        getView().showLoadingDialog();
        userInteractor.getUser(new UserInteractor.GetUserCallback() {
            @Override
            public void getUser(DatabaseError error, User user) {
                getView().hideLoadingDialog();
                if (error != null) {
                    getView().showDatabaseError(error);
                } else {
                    userInteractor.saveCurrentLocalUser(user);
                    getView().showUser(user);
                }
            }
        }, true);
    }

    @Override
    public void deletePost(Post post) {
        getView().showLoadingDialog();

        postInteractor.deletePost(post, new PostInteractor.DeletePostCallback() {
            @Override
            public void listPost(Exception e) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().reloadPost();
                    getView().showMessenger("Đã xóa");
                }
            }
        });
    }

    @Override
    public void changeAvatar(final Uri uri) {
        getView().showLoadingDialog();

        userInteractor.addAvatar(uri, new UserInteractor.AddAvatarCallback() {
            @Override
            public void addAvatar(Exception e, String uri) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showMessenger("Thay đổi avatar thành công!");

                    userInteractor.saveCurrentLocalUser(new User(null, null, null, null, uri, null));

                    getView().showAvatarChanged(uri);
                    getView().reloadPost();
                }
            }
        });
    }

    @Override
    public void changeCover(final Uri uri) {
        getView().showLoadingDialog();

        userInteractor.addCover(uri, new UserInteractor.AddCoverCallback() {
            @Override
            public void addCover(Exception e, String uri) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().showMessenger("Thay đổi ảnh bìa thành công!");

                    userInteractor.saveCurrentLocalUser(new User(null, null, null, null, null, uri));

                    getView().showCoverChanged(uri);
                }
            }
        });
    }
}
