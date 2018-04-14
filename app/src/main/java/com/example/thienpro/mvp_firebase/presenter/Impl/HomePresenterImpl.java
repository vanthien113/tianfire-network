package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class HomePresenterImpl extends BasePresentermpl<HomeView> implements HomePresenter {
    private UserInteractor userInteractor;
    private PostInteractor postInteractor;

    public HomePresenterImpl(UserInteractor userInteractor, PostInteractor postInteractor) {
        this.postInteractor = postInteractor;
        this.userInteractor = userInteractor;
    }

    public void loadAllListPost() {
        getView().showLoadingDialog();

        postInteractor.loadAllPost(new PostInteractor.ListPostCallback() {
            @Override
            public void listPost(DatabaseError e, ArrayList<Post> listPost) {
                getView().hideLoadingDialog();
                if (e == null) {
                    getView().showAllPost(listPost);
                } else {
                    getView().showDatabaseError(e);
                }
            }
        });
    }

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
    public void downloadImage(String imageUrl) {
//        DownloadUltil.startDownload(context, imageUrl);
    }
}
