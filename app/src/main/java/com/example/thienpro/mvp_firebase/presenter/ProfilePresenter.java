package com.example.thienpro.mvp_firebase.presenter;

import com.example.thienpro.mvp_firebase.model.LoadPostListener;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
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

public class ProfilePresenter implements LoadPostListener {
    private PostInteractor postInteractor;
    private ProfileView profileView;

    public ProfilePresenter(ProfileView profileView) { // Truyền tham sô profileview
        postInteractor = new PostInteractor(this);
        this.profileView = profileView;
    }

    @Override
    public void ListPost(ArrayList<Post> list) {
        profileView.showList(list);
    }

    public void loadPost() {
        postInteractor.LoadPersonalPost();
    }

    public void newPost(String content) {
        postInteractor.writeNewPost(content);
    }
}
