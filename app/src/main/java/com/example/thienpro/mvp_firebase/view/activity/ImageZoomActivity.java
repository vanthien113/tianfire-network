package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;
import com.example.thienpro.mvp_firebase.databinding.ActivityImageZoomBinding;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;

public class ImageZoomActivity extends BaseActivity<ActivityImageZoomBinding> {
    private static String IMAGE = "image";

    public static void startActivity(Context context, String imageUrl) {
        Intent intent = new Intent(context, ImageZoomActivity.class);
        intent.putExtra(IMAGE, imageUrl);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_zoom;
    }

    @Override
    protected void init() {
        String imageUrl = getIntent().getStringExtra(IMAGE);
        SHBitmapHelper.bindImage(getBinding().ivImage, imageUrl);
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
