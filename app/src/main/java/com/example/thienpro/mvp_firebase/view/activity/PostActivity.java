package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityPostBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.PostPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.view.PostView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostActivity extends BaseActivity<ActivityPostBinding> implements PostView {
    private PostPresenter presenter;
    private Uri filePath;

    private static final int REQUEST_CODE_IMAGE = 1;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, PostActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    protected void init() {
        presenter = new PostPresenterImpl();
        presenter.attachView(this);

        viewDataBinding.setEvent(this);
    }

    @Override
    public void onBackClick() {
        HomeActivity.startActiviry(this);
    }

    @Override
    public void onPostClick() {
        if (TextUtils.isEmpty(viewDataBinding.etPost.getText()))
            Toast.makeText(this, R.string.hay_nhap_cam_nhan_cua_ban, Toast.LENGTH_SHORT).show();
        else {
            presenter.newPost(viewDataBinding.etPost.getText().toString(), filePath);
        }
    }

    @Override
    public void navigationToHome() {
        HomeActivity.startActiviry(this);
    }

    @Override
    public void onInsertImageClick() {
        ImagePicker.create(this)
                .returnAfterFirst(true)
                .imageTitle("Tap to select")
                .showCamera(true)
                .single()
                .imageDirectory("Camera")
                .start(REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            List<Image> images = data.getParcelableArrayListExtra("selectedImages");
            if (images != null && images.size() > 0) {
                Image image = images.get(0);

                filePath = Uri.fromFile(new File(image.getPath()));
                Bitmap bitmap = BitmapFactory.decodeFile(image.getPath());

                viewDataBinding.ivUploaded.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    protected void startScreen() {

    }

    @Override
    protected void resumeScreen() {

    }

    @Override
    protected void pauseScreen() {

    }

    @Override
    protected void destroyScreen() {

    }
}
