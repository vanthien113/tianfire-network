package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemFriendProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.FriendProfileVH;

import java.util.ArrayList;

public class FriendProfileAdapter extends RecyclerView.Adapter<FriendProfileVH> {
    private ArrayList<Post> listPost;

    public FriendProfileAdapter(ArrayList<Post> listPost) {
        this.listPost = listPost;
    }

    @Override
    public FriendProfileVH onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ItemFriendProfileBinding binding = ItemFriendProfileBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(layoutParams);
        return new FriendProfileVH(binding);
    }

    @Override
    public void onBindViewHolder(FriendProfileVH holder, int position) {
        holder.bind(listPost.get(position));
    }

    @Override
    public int getItemCount() {
        return listPost == null ? 0 : listPost.size();
    }
}
