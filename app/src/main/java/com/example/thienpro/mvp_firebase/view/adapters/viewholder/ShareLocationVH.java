package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.support.v7.widget.RecyclerView.ViewHolder;

import com.example.thienpro.mvp_firebase.databinding.ItemShareLocationBinding;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.view.ItemShareLocationView;
import com.example.thienpro.mvp_firebase.view.adapters.ShareLocationAdapter;

public class ShareLocationVH extends ViewHolder implements ItemShareLocationView {
    private ItemShareLocationBinding binding;
    private ShareLocationAdapter.ListLocationListener listener;

    public ShareLocationVH(ItemShareLocationBinding binding, ShareLocationAdapter.ListLocationListener listener) {
        super(binding.getRoot());

        this.listener = listener;
        this.binding = binding;
        binding.setEvent(this);
    }

    public void bind(UserLocation location) {
        binding.setData(location);
    }

    @Override
    public void onShowUserLocationClick(UserLocation location) {
        listener.showListLocation(location);

    }
}
