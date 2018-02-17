package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
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
        // Load the image using Glide
        Glide.with(binding.getRoot().getContext())
                .load(post.getImage())
                .into(binding.ivImage);

        Glide.with(binding.getRoot().getContext())
                .load(post.getAvatar())
                .asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(binding.ivAvatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(binding.getRoot().getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        binding.ivAvatar.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }
}
