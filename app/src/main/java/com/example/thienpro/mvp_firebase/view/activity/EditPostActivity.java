package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityEditPostBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.presenter.EditPostPresenter;
import com.example.thienpro.mvp_firebase.presenter.Impl.EditPostPresenterImpl;
import com.example.thienpro.mvp_firebase.view.EditPostView;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

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
        presenter = new EditPostPresenterImpl(this);
        presenter.attachView(this);

        post = (Post) getIntent().getSerializableExtra(POST);

        getBinding().setPost(post);
        getBinding().setEvent(this);
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
    public void onPostClick() {
        presenter.editPost(getBinding().getPost());
    }

    @Override
    public void navigationToHome() {
        HomeActivity.startActiviry(this);
    }
}
