package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ItemFriendProfileBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.ultils.DownloadUltil;
import com.example.thienpro.mvp_firebase.ultils.SHStringHelper;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;

public class FriendProfileVH extends RecyclerView.ViewHolder {
    private ItemFriendProfileBinding binding;
    private String picture;

    public FriendProfileVH(ItemFriendProfileBinding binding) {
        super(binding.getRoot());

        this.binding = binding;

        downloadImageEvent();
    }

    public void bind(Post post) {
        picture = post.getImage();

        binding.setData(post);
        SHStringHelper.hashTag(post.getPost(), ContextCompat.getColor(binding.getRoot().getContext(), R.color.colorGreen), binding.tvPost);

        SHBitmapHelper.bindImage(binding.ivImage, post.getImage());
        SHBitmapHelper.bindImage(binding.ivAvatar, post.getAvatar());
    }

    private void downloadImageEvent(){
        binding.ivImage.setOnLongClickListener(new View.OnLongClickListener() {
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
