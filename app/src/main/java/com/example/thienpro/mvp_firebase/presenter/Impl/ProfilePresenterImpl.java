package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
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

    public ProfilePresenterImpl(ProfileView profileView) { // Truyền tham sô profileview
        postInteractor = new PostInteractorImpl();
        this.view = profileView;
    }

    public void loadPost() {
        postInteractor.loadPersonalPost(new PostInteractor.ListPost() {
            @Override
            public void listPost(DatabaseError e, ArrayList<Post> listPost) {
                if (e == null) {
                    view.showList(listPost);
                } else {
                    view.loadPostError(e);
                }
            }
        });
    }

}
