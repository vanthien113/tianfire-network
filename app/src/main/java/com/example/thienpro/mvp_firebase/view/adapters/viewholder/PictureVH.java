package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ItemPictureBinding;
import com.example.thienpro.mvp_firebase.ultils.DownloadUltil;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.activity.ImageZoomActivity;

public class PictureVH extends RecyclerView.ViewHolder {
    private ItemPictureBinding binding;
    private String picture;
    private int type;

    public PictureVH(ItemPictureBinding binding, int type) {
        super(binding.getRoot());
        this.binding = binding;
        this.type = type;
        downloadImageEvent();
    }

    public void bind(String picture) {
        this.picture = picture;

        if (type == 1) {
            binding.ivPicture.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
            binding.ivPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        SHBitmapHelper.bindImage(binding.ivPicture, picture);
    }

    private void downloadImageEvent() {
        binding.ivPicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(binding.getRoot().getContext())
                        .setTitle(R.string.tai_xuong)
                        .setCancelable(true)
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
        binding.ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageZoomActivity.startActivity(binding.getRoot().getContext(), picture);
            }
        });
    }
}
