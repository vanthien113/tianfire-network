package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemPostBinding;
import com.example.thienpro.mvp_firebase.databinding.ItemProfileHeaderBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.ItemPostVH;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.ItemFriendProfileHeaderVH;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.ItemFriendProfilePostVH;

import java.util.ArrayList;
import java.util.List;

public class FriendProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int HEADER = 0;
    private final int POST = 1;
    private List<Post> lisPost;
    private User user;
    private HomeAdapter.ListPostMenuListener listPostMenuListener;
    private ProfileAdapter.ItemProfileClickListener listener;

    public FriendProfileAdapter(User user, HomeAdapter.ListPostMenuListener listPostMenuListener, ProfileAdapter.ItemProfileClickListener listener) {
        this.user = user;
        this.listPostMenuListener = listPostMenuListener;
        this.listener = listener;
    }

    public void updateAdapter(List<Post> mLisPost){
        this.lisPost = mLisPost;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            ItemProfileHeaderBinding binding = ItemProfileHeaderBinding.inflate(LayoutInflater.from(parent.getContext()));
            binding.getRoot().setLayoutParams(LayoutUltils.getRecyclerViewLayoutParams());
            return new ItemFriendProfileHeaderVH(binding, listener);
        } else if (viewType == POST) {
            ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()));
            binding.getRoot().setLayoutParams(LayoutUltils.getRecyclerViewLayoutParams());
            return new ItemFriendProfilePostVH(binding, listPostMenuListener, null);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemFriendProfileHeaderVH) {
            ((ItemFriendProfileHeaderVH) holder).bind(user);
        } else if (holder instanceof ItemPostVH) {
            ((ItemPostVH) holder).bind(lisPost.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case HEADER:
                return HEADER;
            default:
                return POST;
        }
    }

    @Override
    public int getItemCount() {
        return lisPost == null ? 1 : lisPost.size() + 1;
    }
}
