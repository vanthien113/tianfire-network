package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemActivityHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.HomeVH;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/11/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeVH> {
    private ArrayList<Post> mLisPost;

    public HomeAdapter(ArrayList<Post> mLisPost) {
        this.mLisPost = mLisPost;
    }

    @Override
    public HomeVH onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ItemActivityHomeBinding binding = ItemActivityHomeBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(layoutParams);
        return new HomeVH(binding);
    }

    @Override
    public void onBindViewHolder(HomeVH holder, int position) {
        holder.bind(mLisPost.get(position));
    }

    @Override
    public int getItemCount() {
        return mLisPost == null ? 0 : mLisPost.size();
    }
}
