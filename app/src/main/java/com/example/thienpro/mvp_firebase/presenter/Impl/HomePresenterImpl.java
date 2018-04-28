package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.manager.PostManager;
import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.HomePresenter;
import com.example.thienpro.mvp_firebase.view.HomeView;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/21/2017.
 */

public class HomePresenterImpl extends BasePresentermpl<HomeView> implements HomePresenter {
    private UserInteractor userInteractor;
    private PostInteractor postInteractor;
    private PostManager postManager;
    private UserManager userManager;

    private UserManager.OnUserChangeListener onUserChangeListener = new UserManager.OnUserChangeListener() {
        @Override
        public void onChange(User newUser) {
            getView().reloadPost();
        }
    };

    private PostManager.OnPostChangeListener listener = new PostManager.OnPostChangeListener() {
        @Override
        public void onChange() {
            getView().reloadPost();
        }
    };

    public HomePresenterImpl(UserInteractor userInteractor, PostInteractor postInteractor, PostManager postManager, UserManager userManager) {
        this.postInteractor = postInteractor;
        this.userInteractor = userInteractor;
        this.postManager = postManager;
        this.userManager = userManager;
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
        postManager.addOnPostChangeListener(listener);
        userManager.addOnUserChangeListener(onUserChangeListener);
        super.attachView(view);
    }

    @Override
    public void detach() {
        postManager.removePostChangeListener(listener);
        userManager.removeUserChangeListener(onUserChangeListener);
        super.detach();
    }
}
