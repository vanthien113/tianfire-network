package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemListLocationBinding;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.ListLocationVH;

import java.util.ArrayList;

public class ListLocationAdapter extends RecyclerView.Adapter<ListLocationVH> {
    private ArrayList<UserLocation> listLocation;
    private ListLocationAdapter.ListLocationListener listener;

    public ListLocationAdapter(ArrayList<UserLocation> listLocation, ListLocationAdapter.ListLocationListener listener) {
        this.listener = listener;
        this.listLocation = listLocation;
    }

    @Override
    public ListLocationVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListLocationBinding binding = ItemListLocationBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(LayoutUltils.getRecyclerViewLayoutParams());
        return new ListLocationVH(binding, listener);
    }

    @Override
    public void onBindViewHolder(ListLocationVH holder, int position) {
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
