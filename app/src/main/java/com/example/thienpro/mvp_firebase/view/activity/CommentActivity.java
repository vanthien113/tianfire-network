package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.text.TextUtils;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;
import com.example.thienpro.mvp_firebase.databinding.ActivityCommentBinding;
import com.example.thienpro.mvp_firebase.model.entity.Comment;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.CommentPresenter;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.CommentView;
import com.example.thienpro.mvp_firebase.view.adapters.CommentAdapter;

import java.util.List;

public class CommentActivity extends BaseActivity<ActivityCommentBinding> implements CommentView, CommentAdapter.ItemCommentClickListener {
    private static String POST = "comment";

    private CommentAdapter adapter;
    private CommentPresenter presenter;
    private Post post;
    private User currentUser;

    public static void startActivity(Context context, Post post) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(POST, post);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void init() {
        getBinding().setEvent(this);
        presenter = getAppComponent().getCommonComponent().getCommentPresenter();
        presenter.attachView(this);

        post = (Post) getIntent().getSerializableExtra(POST);
        currentUser = getAppComponent().getUserManager().getUser();

        adapter = new CommentAdapter(post, currentUser, this);

        getBinding().rvComment.setLayoutManager(LayoutUltils.getLinearLayoutManager(this));
        getBinding().rvComment.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        getBinding().rvComment.setAdapter(adapter);

        presenter.getComment(post.getTimePost());
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
    public void onCommentClick() {
        String content = getBinding().etComment.getText().toString();
        if (validate(content))
            presenter.writeComment(content, post.getTimePost());
    }

    private boolean validate(String content) {
        if (TextUtils.isEmpty(content)) {
            showToastMessage(R.string.ban_hay_nhap_binh_luan_truoc_khi_dang);
            return false;
        }
        return true;
    }

    @Override
    public void showComments(List<Comment> comments) {
        adapter.updateAdapter(comments);
        getBinding().rvComment.scrollToPosition(comments.size());
    }

    @Override
    public void clearComment() {
        getBinding().etComment.setText(null);
    }

    @Override
    public void showDeleteCommentMessage() {
        showToastMessage(R.string.da_xoa);
    }

    @Override
    public void onDeleteComment(Comment data) {
        presenter.deleteComment(data, post.getTimePost());
    }

    @Override
    public void onUserClick(String userId) {
        FriendProfileActivity.startActivity(this, userId);
    }
}
