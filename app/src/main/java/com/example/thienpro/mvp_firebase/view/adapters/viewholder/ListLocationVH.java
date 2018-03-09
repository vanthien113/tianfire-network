package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.support.v7.widget.RecyclerView.ViewHolder;

import com.example.thienpro.mvp_firebase.databinding.ItemListLocationBinding;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.view.adapters.ListLocationAdapter;

public class ListLocationVH extends ViewHolder implements UserLocationEH {
    private ItemListLocationBinding binding;
    private ListLocationAdapter.ListLocationListener listener;

    public ListLocationVH(ItemListLocationBinding binding, ListLocationAdapter.ListLocationListener listener) {
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
