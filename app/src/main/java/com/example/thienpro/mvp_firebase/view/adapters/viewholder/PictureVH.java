package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.thienpro.mvp_firebase.databinding.ItemPictureBinding;
import com.example.thienpro.mvp_firebase.ultils.DownloadUltil;
import com.example.thienpro.mvp_firebase.view.ItemPictureView;

public class PictureVH extends RecyclerView.ViewHolder implements ItemPictureView {
    private ItemPictureBinding binding;
    private AlertDialog.Builder alertDialog;

    public PictureVH(ItemPictureBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        binding.setEvent(this);
        alertDialog = new AlertDialog.Builder(binding.getRoot().getContext());
    }

    public void bind(String picture) {
        binding.setUrl(picture);

        Glide.with(binding.getRoot().getContext())
                .load(picture)
                .into(binding.ivPicture);

    }

    @Override
    public void onImageClick(final String imageUrl) {
        alertDialog.setTitle("Tải xuống")
                .setCancelable(false)
                .setPositiveButton("Tải", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DownloadUltil.startDownload(binding.getRoot().getContext(), imageUrl);
                    }
                })
                .setNegativeButton("Hủy", null)
                .setMessage("Tải ảnh xuống máy của bạn");

        binding.ivPicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                alertDialog.show();
                return false;
            }
        });
    }
}
