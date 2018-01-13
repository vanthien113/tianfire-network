package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityPostBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.PostPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.view.PostView;

import java.io.IOException;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostActivity extends AppCompatActivity implements PostView {
    private ActivityPostBinding binding;
    private PostPresenter postPresenter;
    private Uri filePath;
    private static final int REQUEST_CODE_IMAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        postPresenter = new PostPresenterImpl(this);
        binding.setEvent(this);
    }

    @Override
    public void onBackClick() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPostClick() {
        if (TextUtils.isEmpty(binding.etPost.getText()))
            Toast.makeText(this, "Hãy nhập cảm nhận của bạn!", Toast.LENGTH_SHORT).show();
        else {
            postPresenter.newPost(binding.etPost.getText().toString(), filePath);
            posted();
        }
    }

    @Override
    public void posted() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onChoosePictureClick() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                binding.ivUploaded.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
