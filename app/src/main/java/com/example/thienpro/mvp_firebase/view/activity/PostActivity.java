package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;
import com.example.thienpro.mvp_firebase.databinding.ActivityPostBinding;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.PostView;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostActivity extends BaseActivity<ActivityPostBinding> implements PostView {
    public static final int REQUEST_CODE_IMAGE = 1;

    private PostPresenter presenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, PostActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post;
    }

    @Override
    protected void init() {
        presenter = getAppComponent().getCommonComponent().getPostPresenter();
        presenter.attachView(this);
        getBinding().setEvent(this);

        getBinding().tbPost.getIvChoosePhoto().setOnClickListener(view -> SHBitmapHelper.takePhoto(PostActivity.this, REQUEST_CODE_IMAGE));

        getBinding().tbPost.getIvPost().setOnClickListener(view -> {
            String post = getBinding().etPost.getText().toString();
            if (validate(post)) {
                presenter.newPost(post);
            }
        });
    }

    private boolean validate(String post) {
        if (TextUtils.isEmpty(post)) {
            Toast.makeText(this, R.string.hay_nhap_cam_nhan_cua_ban, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void navigationToHome() {
        onBackPressed();
    }

    @Override
    public void onDeleteImageClick() {
        presenter.deleteImage();

        getBinding().ivUploaded.setImageBitmap(null);
        getBinding().ivDeleteImage.setVisibility(View.GONE);
    }

    @Override
    public void showImageBitmap(Bitmap bitmap) {
        getBinding().ivUploaded.setImageBitmap(bitmap);
    }

    @Override
    public void showDeleteImage() {
        getBinding().ivDeleteImage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(this, requestCode, resultCode, data);
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
