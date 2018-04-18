package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.view.View;

import com.example.thienpro.mvp_firebase.databinding.ItemProfileHeaderBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.adapters.ProfileAdapter;

public class ItemFriendProfileHeaderVH extends ItemProfileHeaderVH {
    private ItemProfileHeaderBinding binding;

    public ItemFriendProfileHeaderVH(ItemProfileHeaderBinding binding, ProfileAdapter.ItemProfileClickListener listener) {
        super(binding, listener);
        this.binding = binding;
    }

    @Override
    public void bind(User user) {
        super.bind(user);

        binding.ivChangeAvatar.setVisibility(View.GONE);
        binding.ivChangeCover.setVisibility(View.GONE);
        binding.tvPost.setVisibility(View.GONE);
    }
}
