package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemPictureBinding;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.PictureVH;

import java.util.ArrayList;

public class PictureAdapter extends RecyclerView.Adapter<PictureVH> {
    private ArrayList<String> listPicture;

    public PictureAdapter(ArrayList<String> listPicture) {
        this.listPicture = listPicture;
    }

    @Override
    public PictureVH onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ItemPictureBinding binding = ItemPictureBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(layoutParams);
        return new PictureVH(binding);
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
