package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityPostBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.PostPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.ultils.LoadingDialog;
import com.example.thienpro.mvp_firebase.view.PostView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

import java.io.IOException;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostActivity extends BaseActivity<ActivityPostBinding> implements PostView {
    private PostPresenter presenter;
    private Uri filePath;
    private LoadingDialog loadingDialog;
    private PopupMenu popupMenu;

    private static final int REQUEST_CODE_IMAGE = 1;
    private static int CODE_FROM_GALLERY_CAMERA = 2;

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

        loadingDialog = new LoadingDialog(this);
        popupMenu = new PopupMenu(this, viewDataBinding.ivPost);

        popupMenu.getMenuInflater().inflate(R.menu.menu_post, popupMenu.getMenu());
        viewDataBinding.setEvent(this);
    }

    @Override
    public void onBackClick() {
        HomeActivity.startActiviry(this);
    }

    @Override
    public void onPostClick() {
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.mn_post:
                        post();
                        break;
                    case R.id.mn_choose_picture:
                        onChoosePicture();
                        break;
                    case R.id.mn_camera:
                        onCamera();
                        break;
                }
                return false;
            }
        });
    }

    private void post() {
        if (TextUtils.isEmpty(viewDataBinding.etPost.getText()))
            Toast.makeText(this, R.string.hay_nhap_cam_nhan_cua_ban, Toast.LENGTH_SHORT).show();
        else {
            presenter.newPost(viewDataBinding.etPost.getText().toString(), filePath);
        }
    }

    private void onCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            this.startActivityForResult(takePictureIntent, CODE_FROM_GALLERY_CAMERA);
        }
    }

    private void onChoosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_IMAGE);
    }

    @Override
    public void onPostFail(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigationToHome() {
        HomeActivity.startActiviry(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                viewDataBinding.ivUploaded.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CODE_FROM_GALLERY_CAMERA && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            viewDataBinding.ivUploaded.setImageBitmap(bitmap);
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
