package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.support.v7.widget.RecyclerView.ViewHolder;

import com.example.thienpro.mvp_firebase.databinding.ItemListLocationBinding;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;

public class ListLocationVH extends ViewHolder implements UserLocationEH {
    private ItemListLocationBinding binding;
    private ListLocationListener listener;

    public ListLocationVH(ItemListLocationBinding binding, ListLocationListener listener) {
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
