package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.support.v7.widget.RecyclerView;

import com.example.thienpro.mvp_firebase.databinding.ItemCommentBinding;
import com.example.thienpro.mvp_firebase.model.entity.Comment;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;

public class ItemCommentVH extends RecyclerView.ViewHolder {
    private ItemCommentBinding binding;

    public ItemCommentVH(ItemCommentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Comment comment) {
        binding.setData(comment);
        SHBitmapHelper.bindImage(binding.ivAvatar, comment.getUserAvatar());
    }
}
