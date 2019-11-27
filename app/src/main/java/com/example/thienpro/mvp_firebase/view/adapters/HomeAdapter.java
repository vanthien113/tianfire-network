package com.example.thienpro.mvp_firebase.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.thienpro.mvp_firebase.databinding.ItemPostBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.ItemPostVH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThienPro on 11/11/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<ItemPostVH> {
    private List<Post> mLisPost;
    private Context context;
    private ListPostMenuListener listener;
    private User user;

    public HomeAdapter(Context context, ListPostMenuListener listener, User user) {
        this.context = context;
        this.listener = listener;
        this.user = user;
    }

    public void updateAdapter(List<Post> mLisPost) {
        this.mLisPost = mLisPost;
        notifyDataSetChanged();
    }

    @Override
    public ItemPostVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(LayoutUltils.getRecyclerViewLayoutParams());
        return new ItemPostVH(binding, listener, user);
    }

    @Override
    public void onBindViewHolder(ItemPostVH holder, int position) {
//        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//        animation.setDuration(1000);
//        holder.itemView.startAnimation(animation);

        holder.bind(mLisPost.get(position));
    }

    @Override
    public int getItemCount() {
        return mLisPost == null ? 0 : mLisPost.size();
    }

    public interface ListPostMenuListener {
        void onEditPost(Post post);

        void onDeletePost(Post post);

        void onDownload(String imageUrl);

        void onFriendProfile(String userId);

        void onImageClick(String imageUrl);

        void onCommentClick(Post post);
    }
}
