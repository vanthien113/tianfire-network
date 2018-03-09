package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thienpro.mvp_firebase.databinding.ItemPictureBinding;

public class PictureVH extends RecyclerView.ViewHolder {
    private ItemPictureBinding binding;

    public PictureVH(ItemPictureBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String picture) {
        Glide.with(binding.getRoot().getContext())
                .load(picture)
                .into(binding.ivPicture);

    }
}
