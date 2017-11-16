package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.support.v7.widget.RecyclerView;

import com.example.thienpro.mvp_firebase.databinding.ItemActivityHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;

/**
 * Created by ThienPro on 11/11/2017.
 */

public class HomeVH extends RecyclerView.ViewHolder {
    ItemActivityHomeBinding binding;

    public HomeVH(ItemActivityHomeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Post post) {
        binding.setData(post);
    }
}
