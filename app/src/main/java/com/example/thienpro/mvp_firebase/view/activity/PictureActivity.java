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
    private PicturePresenter presenter;
    private PictureAdapter adapter;

    public static void startActivity(Context context) {
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

        viewDataBinding.setEvent(this);

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
        viewDataBinding.rvPicture.setAdapter(adapter);
    }

    @Override
    public void onChangeViewTypeClick() {
        if (viewDataBinding.rbOne.isChecked()) {
            viewDataBinding.rvPicture.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        } else {
            viewDataBinding.rvPicture.setLayoutManager(new GridLayoutManager(this, 2));
        }

        presenter.getPicture();
    }
}
