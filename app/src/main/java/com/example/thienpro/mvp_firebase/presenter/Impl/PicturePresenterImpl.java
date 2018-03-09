package com.example.thienpro.mvp_firebase.presenter.Impl;

import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.presenter.PicturePresenter;
import com.example.thienpro.mvp_firebase.view.PictureView;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class PicturePresenterImpl extends BasePresentermpl<PictureView> implements PicturePresenter {
    private PostInteractor postInteractor;

    public PicturePresenterImpl() {
        this.postInteractor = new PostInteractorImpl();
    }

    @Override
    public void getPicture() {
        getView().showLoadingDialog();

        postInteractor.getPicture(new PostInteractor.GetPictureCallback() {
            @Override
            public void getPicture(DatabaseError e, ArrayList<String> listPicture) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showDatabaseError(e);
                } else {
                    getView().showPicture(listPicture);
                }
            }
        });
    }
}
