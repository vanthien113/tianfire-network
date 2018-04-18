package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.esafirm.imagepicker.model.Image;
import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.view.PostView;
import com.example.thienpro.mvp_firebase.view.activity.PostActivity;
import com.example.thienpro.mvp_firebase.view.bases.BasePresentermpl;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostPresenterImpl extends BasePresentermpl<PostView> implements PostPresenter {
    private PostInteractor postInteractor;
    private Uri filePath;

    public PostPresenterImpl(PostInteractor postInteractor) {
        this.postInteractor = postInteractor;
    }

    @Override
    public void newPost(String content) {
        getView().showLoadingDialog();

        postInteractor.writeNewPost(content, filePath, new PostInteractor.PostCallback() {
            @Override
            public void postListener(Exception e) {
                getView().hideLoadingDialog();
                if (e != null) {
                    getView().showExceptionError(e);
                } else {
                    getView().navigationToHome();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PostActivity.REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                filePath = Uri.fromFile(new File(image.getPath()));
                Bitmap bitmap = BitmapFactory.decodeFile(image.getPath());

                getView().showImageBitmap(bitmap);
                getView().showDeleteImage();
            }
        }
    }

    @Override
    public void deleteImage() {
        filePath = null;
    }
}


