package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemShareLocationBinding;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.ShareLocationVH;

import java.util.List;

public class ShareLocationAdapter extends RecyclerView.Adapter<ShareLocationVH> {
    private List<UserLocation> listLocation;
    private ShareLocationAdapter.ListLocationListener listener;

    public ShareLocationAdapter(ShareLocationAdapter.ListLocationListener listener) {
        this.listener = listener;
    }

    public void updateAdapter(List<UserLocation> listLocation) {
        this.listLocation = listLocation;
        notifyDataSetChanged();
    }

    @Override
    public ShareLocationVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemShareLocationBinding binding = ItemShareLocationBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(LayoutUltils.getRecyclerViewLayoutParams());
        return new ShareLocationVH(binding, listener);
    }

    @Override
    public void onBindViewHolder(ShareLocationVH holder, int position) {
        holder.bind(listLocation.get(position));
    }

    @Override
    public int getItemCount() {
        return listLocation == null ? 0 : listLocation.size();
    }

    public interface ListLocationListener {
        void showListLocation(UserLocation location);
    }
}
