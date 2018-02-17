package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

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

        postInteractor.loadPersonalPost(new PostInteractor.ListPost() {
            @Override
            public void listPost(DatabaseError e, ArrayList<Post> listPost) {
                if (e == null) {
                    view.hideLoading();
                    view.showList(listPost);
                } else {
                    view.hideLoading();
                    view.loadPostError(e);
                }
            }
        });
    }

    public void getCurrentUser() {
        userInteractor.getUser(new UserInteractor.GetUserListener() {
            @Override
            public void getUser(DatabaseError error, User user) {
            }
        }, true);
    }
}
