package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemPictureBinding;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.PictureVH;

import java.util.ArrayList;

public class PictureAdapter extends RecyclerView.Adapter<PictureVH> {
    private ArrayList<String> listPicture;
    private int type;

    public PictureAdapter(ArrayList<String> listPicture, int type) {
        this.listPicture = listPicture;
        this.type = type;
    }

    @Override
    public PictureVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPictureBinding binding = ItemPictureBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(LayoutUltils.getRecyclerViewLayoutParams());
        return new PictureVH(binding, type);
    }

    @Override
    public void onBindViewHolder(PictureVH holder, int position) {
        holder.bind(listPicture.get(position));
    }

    @Override
    public int getItemCount() {
        return listPicture == null ? 0 : listPicture.size();
    }
}
