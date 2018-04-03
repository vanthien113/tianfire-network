package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.support.v7.widget.RecyclerView;

import com.example.thienpro.mvp_firebase.databinding.ItemFriendProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.ultils.SHBitmapHelper;

public class FriendProfileVH extends RecyclerView.ViewHolder {
    private ItemFriendProfileBinding binding;

    public FriendProfileVH(ItemFriendProfileBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public void bind(Post post) {
        binding.setData(post);

        SHBitmapHelper.bindImage(binding.ivImage, post.getImage());

        SHBitmapHelper.bindCircularImage(binding.ivAvatar, post.getAvatar());
    }
}
