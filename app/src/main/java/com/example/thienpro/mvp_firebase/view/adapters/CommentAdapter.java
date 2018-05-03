package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemCommentBinding;
import com.example.thienpro.mvp_firebase.databinding.ItemPostBinding;
import com.example.thienpro.mvp_firebase.model.entity.Comment;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.ItemCommentPostVH;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.ItemCommentVH;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.ItemPostVH;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int POST = 0;
    private static int COMMENT = 1;

    private Post post;
    private List<Comment> comments;
    private User currentUser;

    public CommentAdapter(Post post, User currentUser) {
        this.post = post;
        this.currentUser = currentUser;
    }

    public void updateAdapter(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == POST) {
            ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()));
            binding.getRoot().setLayoutParams(LayoutUltils.getRecyclerViewLayoutParams());
            return new ItemCommentPostVH(binding, null, currentUser);
        }

        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(LayoutUltils.getRecyclerViewLayoutParams());

        return new ItemCommentVH(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemCommentPostVH) {
            ((ItemPostVH) holder).bind(post);
        }
        if (holder instanceof ItemCommentVH) {
            ((ItemCommentVH) holder).bind(comments.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return POST;
        }
        return COMMENT;
    }

    @Override
    public int getItemCount() {
        return comments == null ? 1 : comments.size() + 1;
    }
}
