package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;
import com.example.thienpro.mvp_firebase.databinding.ActivityEditPostBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.EditPostPresenter;
import com.example.thienpro.mvp_firebase.view.EditPostView;

public class EditPostActivity extends BaseActivity<ActivityEditPostBinding> implements EditPostView {
    private static String POST = "post";

    private Post post;
    private EditPostPresenter presenter;

    public static void start(Context context, Post post) {
        Intent intent = new Intent(context, EditPostActivity.class);
        intent.putExtra(POST, post);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_post;
    }

    @Override
    protected void init() {
        presenter = getAppComponent().getCommonComponent().getEditPostPresenter();
        presenter.attachView(this);

        post = (Post) getIntent().getSerializableExtra(POST);

        getBinding().setPost(post);
        getBinding().setEvent(this);

        getBinding().tbEditPost.getIvPost().setOnClickListener(view -> {
            if (validate(getBinding().getPost().getPost()))
                presenter.editPost(getBinding().getPost());
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
        presenter.detach();

    }

    @Override
    public void navigationToHome() {
        onBackPressed();
    }
}
