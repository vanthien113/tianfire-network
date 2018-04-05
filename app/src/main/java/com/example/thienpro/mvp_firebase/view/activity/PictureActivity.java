package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityPictureBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.PicturePresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.PicturePresenter;
import com.example.thienpro.mvp_firebase.view.PictureView;
import com.example.thienpro.mvp_firebase.view.adapters.PictureAdapter;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

import java.util.ArrayList;

public class PictureActivity extends BaseActivity<ActivityPictureBinding> implements PictureView {
    private static String USER_ID = "userId";

    private PicturePresenter presenter;
    private PictureAdapter adapter;
    private String userId;

    public static void startActivity(Context context, String userId) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(new Intent(context, PictureActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    protected void init() {
        presenter = new PicturePresenterImpl();
        presenter.attachView(this);

        userId = getIntent().getStringExtra(USER_ID);
        getBinding().setEvent(this);

        onChangeViewTypeClick();
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
            getBinding().rvPicture.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        } else {
            getBinding().rvPicture.setLayoutManager(new GridLayoutManager(this, 2));
        }

        presenter.getPicture(userId);
    }
}
