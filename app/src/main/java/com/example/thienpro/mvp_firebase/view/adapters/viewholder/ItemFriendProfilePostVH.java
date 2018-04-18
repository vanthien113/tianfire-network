package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.view.View;

import com.example.thienpro.mvp_firebase.databinding.ItemActivityHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;

/**
 * Created by ThienPro on 11/11/2017.
 */

public class ItemFriendProfilePostVH extends HomeVH {
    public ItemFriendProfilePostVH(ItemActivityHomeBinding binding, HomeAdapter.ListPostMenuListener listener, User user) {
        super(binding, listener, user);
    }

    @Override
    public void bind(Post post) {
        super.bind(post);
        binding.ibMenu.setVisibility(View.GONE);
    }
}
