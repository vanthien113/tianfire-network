package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemActivityHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.HomeVH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThienPro on 11/11/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeVH>{
    private ItemActivityHomeBinding binding;
    private ArrayList<Post> mLisPost;
    private loadmore loadmore;

    public HomeAdapter(ArrayList<Post> mLisPost, loadmore loadmore) {
        this.mLisPost = mLisPost;
        this.loadmore = loadmore;
    }

    @Override
    public HomeVH onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding = ItemActivityHomeBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(layoutParams);
        return new HomeVH(binding);
    }

    @Override
    public void onBindViewHolder(HomeVH holder, int position) {
        holder.bind(mLisPost.get(position));
        if (position + 5 == mLisPost.size()){
            if (loadmore!=null){
                loadmore.onLoadmore();
            }
        }

        //holder.bind(mLisPost.get(position));//mLisPost.size() - position - 1));
    }

    @Override
    public int getItemCount() {
        return mLisPost == null ? 0 : mLisPost.size();
    }

    public void appendItem(@NonNull List<Post> items){
        if (items.size() == 0){
            return;
        }
        int size = items.size();
        int currentSize = mLisPost.size();
        mLisPost.addAll(items);
        notifyItemRangeInserted(currentSize, size);
    }
}
