package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.ultils.DownloadUltil;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class HomePresenterImpl extends BasePresentermpl<HomeView> implements HomePresenter {
    private PostInteractor postInteractor;
    private UserInteractor userInteractor;
    private Context context;

    public HomePresenterImpl(Context context) {
        this.context = context;
        postInteractor = new PostInteractorImpl();
        userInteractor = new UserInteractorImpl(context);
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

    @Override
    public void currentUser() {
        userInteractor.loadCurrentLocalUser(new UserInteractor.LoadCurrentLocalUserCallback() {
            @Override
            public void currentLocalUser(User user) {
                getView().currentUser(user);
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
        DownloadUltil.startDownload(context, imageUrl);
    }
}
