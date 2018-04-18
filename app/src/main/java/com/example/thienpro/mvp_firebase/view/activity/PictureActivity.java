package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityPictureBinding;
import com.example.thienpro.mvp_firebase.presenter.PicturePresenter;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.PictureView;
import com.example.thienpro.mvp_firebase.view.adapters.PictureAdapter;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;

import java.util.ArrayList;

public class PictureActivity extends BaseActivity<ActivityPictureBinding> implements PictureView {
    private static String USER_ID = "userId";

    private PicturePresenter presenter;
    private PictureAdapter adapter;
    private String userId;

    public static void startActivity(Context context, String userId) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    protected void init() {
        presenter = getAppComponent().getCommonComponent().getPicturePresenter();
        presenter.attachView(this);

        userId = getIntent().getStringExtra(USER_ID);
        getBinding().setEvent(this);

        onChangeViewTypeClick();

        getBinding().tbPicture.getImageBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

    @Override
    public void showPicture(ArrayList<String> listPicture) {
        adapter = new PictureAdapter(listPicture);
        getBinding().rvPicture.setAdapter(adapter);
    }

    @Override
    public void onChangeViewTypeClick() {
        if (getBinding().rbOne.isChecked()) {
            getBinding().rvPicture.setLayoutManager(LayoutUltils.getLinearLayoutManager(this));
        } else {
            getBinding().rvPicture.setLayoutManager(new GridLayoutManager(this, 2));
        }

        presenter.getPicture(userId);
    }
}
