package com.example.thienpro.mvp_firebase.presenter.Impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.esafirm.imagepicker.model.Image;
import com.example.thienpro.mvp_firebase.bases.BasePresentermpl;
import com.example.thienpro.mvp_firebase.manager.PostManager;
import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.model.Impl.BaseInteractorImpl;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.ultils.SHDateTimeFormat;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.PostView;
import com.example.thienpro.mvp_firebase.view.activity.PostActivity;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostPresenterImpl extends BasePresentermpl<PostView> implements PostPresenter {
    private PostInteractor postInteractor;
    private Uri filePath;
    private UserManager userManager;
    private PostManager postManager;

    public PostPresenterImpl(PostInteractor postInteractor, UserManager userManager, PostManager postManager) {
        this.postInteractor = postInteractor;
        this.userManager = userManager;
        this.postManager = postManager;
    }

    @Override
    public void newPost(final String content) {
        if (getView() == null)
            return;
        getView().showLoadingDialog();

        if (filePath != null) {
            postInteractor.uploadImage(filePath, BaseInteractorImpl.IMAGES, userManager.getUser().getId(), (e, string) -> post(content, string));
        } else post(content, null);
    }

    private void post(String content, String imageUrl) {
        postInteractor.writeNewPost(userManager.getUser().getName(), userManager.getUser().getAvatar(), content, imageUrl, SHDateTimeFormat.getPostCurrentTime(), e -> {
            if (getView() == null)
                return;
            getView().hideLoadingDialog();
            if (e != null) {
                getView().showExceptionError(e);
            } else {
                postManager.postChange();
                getView().navigationToHome();
            }
        });
    }

    @Override
    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == PostActivity.REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                filePath = SHBitmapHelper.getUriAndCompressBitmap(context, image.getPath());
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


