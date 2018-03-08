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

public class ProfilePresenterImpl implements ProfilePresenter {
    private PostInteractor postInteractor;
    private ProfileView view;
    private UserInteractor userInteractor;

    public ProfilePresenterImpl(ProfileView profileView, Context context) { // Truyền tham sô profileview
        postInteractor = new PostInteractorImpl();
        userInteractor = new UserInteractorImpl(context);

        this.view = profileView;
    }

    public void loadPost() {
        view.showLoading();

        postInteractor.loadPersonalPost(new PostInteractor.ListPostCallback() {
            @Override
            public void listPost(DatabaseError e, ArrayList<Post> listPost) {
                view.hideLoading();

                if (e == null) {
                    view.showList(listPost);
                } else {
                    view.loadPostError(e);
                }
            }
        });
    }
//
//    @Override
//    public void getCurrentUser() {
//        view.showLoading();
//
//        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserCallback() {
//            @Override
//            public void currentLocalUser(User user) {
//                view.showUser(user);
//                LogUltil.log(getClass(), user.getAvatar());
//            }
//        });
//    }

    @Override
    public void getUser() {
        view.showLoading();
        userInteractor.getUser(new UserInteractor.GetUserCallback() {
            @Override
            public void getUser(DatabaseError error, User user) {
                view.hideLoading();

                if (error != null) {
                } else {
                    userInteractor.saveCurrentLocalUser(user);
                    view.showUser(user);
                }
            }
        }, true);
    }

    @Override
    public void deletePost(Post post) {
        view.showLoading();

        postInteractor.deletePost(post, new PostInteractor.DeletePostCallback() {
            @Override
            public void listPost(Exception e) {
                view.hideLoading();

                if (e != null) {
                    view.showError(e);
                } else {
                    view.showMessenger("Đã xóa");
                }
            }
        });
    }

    @Override
    public void changeAvatar(final Uri uri) {
        view.showLoading();

        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserCallback() {
            @Override
            public void currentLocalUser(final User user) {
                userInteractor.addAvatar(user.getEmail(), user.getName(), user.getAddress(), user.getSex(), uri, user.getCover(), new UserInteractor.AddAvatarCallback() {
                    @Override
                    public void addAvatar(Exception e, String uri) {
                        view.hideLoading();
                        if (e != null) {
                            view.showError(e);
                        } else {
                            view.showMessenger("Thay đổi avatar thành công!");

                            user.setAvatar(uri);
                            userInteractor.saveCurrentLocalUser(user);

                            view.showUser(user);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void changeCover(final Uri uri) {
        view.showLoading();

        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserCallback() {
            @Override
            public void currentLocalUser(final User user) {
                userInteractor.addCover(user.getEmail(), user.getName(), user.getAddress(), user.getSex(), user.getAvatar(), uri, new UserInteractor.AddCoverCallback() {
                    @Override
                    public void addCover(Exception e, String uri) {
                        view.hideLoading();
                        if (e != null) {
                            view.showError(e);
                        } else {
                            view.showMessenger("Thay đổi ảnh bìa thành công!");

                            user.setCover(uri);
                            userInteractor.saveCurrentLocalUser(user);

                            view.showUser(user);
                        }
                    }
                });
            }
        });
    }
}
