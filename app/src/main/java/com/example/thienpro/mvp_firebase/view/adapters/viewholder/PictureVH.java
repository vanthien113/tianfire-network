package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ItemPictureBinding;
import com.example.thienpro.mvp_firebase.ultils.DownloadUltil;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.ItemPictureView;

public class PictureVH extends RecyclerView.ViewHolder implements ItemPictureView {
    private ItemPictureBinding binding;
    private String picture;

    public PictureVH(ItemPictureBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        downloadImageEvent();
    }

    public void bind(String picture) {
        this.picture = picture;
        SHBitmapHelper.bindImage(binding.ivPicture, picture);
    }

    private void downloadImageEvent(){
        binding.ivPicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(binding.getRoot().getContext())
                        .setTitle(R.string.tai_xuong)
                        .setCancelable(false)
                        .setPositiveButton(R.string.tai, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DownloadUltil.startDownload(binding.getRoot().getContext(), picture);
                            }
                        })
                        .setNegativeButton(R.string.huy, null)
                        .setMessage(R.string.tai_anh_xuong_may_cua_ban)
                        .create()
                        .show();
                return false;
            }
        });
    }
}
