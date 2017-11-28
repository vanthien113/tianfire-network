package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.ProfilePresenter;
import com.example.thienpro.mvp_firebase.view.ProfileView;

import java.util.ArrayList;

/**
 * - Presenter: Là lớp xử lý logic từ dữ liệu nhận được.
 * - Nhận dữ liệu từ lớp Model
 * - Đẩy dữ liệu lên lớp V.
 */

/**
 * Created by ThienPro on 11/16/2017.
 */

public class ProfilePresenterImpl implements PostInteractor.LoadPostListener, ProfilePresenter {
    private PostInteractor postInteractor;
    private ProfileView profileView;

    public ProfilePresenterImpl(ProfileView profileView) { // Truyền tham sô profileview
        postInteractor = new PostInteractorImpl(this);
        this.profileView = profileView;
    }

    @Override
    public void ListPost(ArrayList<Post> list) {
        profileView.showList(list);
    }

    public void loadPost() {
        postInteractor.LoadPersonalPost();
    }

}
