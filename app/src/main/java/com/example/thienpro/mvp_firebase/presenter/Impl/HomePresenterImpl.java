package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.manager.PostManager;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class HomePresenterImpl extends BasePresentermpl<HomeView> implements HomePresenter {
    private UserInteractor userInteractor;
    private PostInteractor postInteractor;
    private PostManager postManager;
    private PostManager.OnPostChangeListener listener = new PostManager.OnPostChangeListener() {
        @Override
        public void onChange() {
            getView().reloadPost();
        }
    };

    public HomePresenterImpl(UserInteractor userInteractor, PostInteractor postInteractor, PostManager postManager) {
        this.postInteractor = postInteractor;
        this.userInteractor = userInteractor;
        this.postManager = postManager;
    }

    public void loadAllListPost() {
        if (getView() == null)
            return;
        getView().showLoadingPb();

        postInteractor.loadAllPost(new PostInteractor.ListPostCallback() {
            @Override
            public void onFinish(DatabaseError e, ArrayList<Post> listPost) {
                if (getView() == null)
                    return;
                getView().hideLoadingPb();
                if (e == null) {
                    getView().showAllPost(listPost);
                } else {
                    getView().showDatabaseError(e);
                }
            }
        });
    }

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
                    postManager.postChange();
                    getView().showDeteleComplete();
                }
            }
        });
    }

    @Override
    public void attachView(HomeView view) {
        super.attachView(view);
        postManager.addOnPostChangeListener(listener);
    }

    @Override
    public void detach() {
        super.detach();
        postManager.removePostChangeListener(listener);
    }
}
