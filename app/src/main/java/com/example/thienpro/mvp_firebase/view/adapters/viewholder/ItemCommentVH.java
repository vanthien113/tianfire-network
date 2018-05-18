package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ItemCommentBinding;
import com.example.thienpro.mvp_firebase.model.entity.Comment;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.ItemCommentView;
import com.example.thienpro.mvp_firebase.view.adapters.CommentAdapter;

public class ItemCommentVH extends RecyclerView.ViewHolder implements ItemCommentView {
    private ItemCommentBinding binding;
    private CommentAdapter.ItemCommentClickListener listener;
    private User currentUser;
    private Comment comment;

    public ItemCommentVH(final ItemCommentBinding binding, final CommentAdapter.ItemCommentClickListener listener, User currentUser) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
        this.currentUser = currentUser;

        binding.setEvent(this);
    }

    public void bind(Comment comment) {
        this.comment = comment;
        binding.setData(comment);
        SHBitmapHelper.bindImage(binding.ivAvatar, comment.getUserAvatar());

        setEvent();
    }

    private void setEvent() {
        binding.clComment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (comment.getUserId().equals(currentUser.getId())) {
                    new AlertDialog.Builder(binding.getRoot().getContext())
                            .setTitle(R.string.xoa)
                            .setMessage(R.string.ban_muon_xoa_binh_luan_nay)
                            .setPositiveButton(R.string.xoa, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    listener.onDeleteComment(binding.getData());
                                }
                            })
                            .setNegativeButton(R.string.huy, null)
                            .setCancelable(true)
                            .create()
                            .show();
                }
                return false;
            }
        });
    }

    @Override
    public void onUserClick(String userId) {
        listener.onUserClick(userId);
    }
}
