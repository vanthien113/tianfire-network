package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ItemActivityHomeBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.view.ItemListPostView;
import com.example.thienpro.mvp_firebase.view.adapters.HomeAdapter;

/**
 * Created by ThienPro on 11/11/2017.
 */

public class HomeVH extends RecyclerView.ViewHolder implements ItemListPostView {
    private ItemActivityHomeBinding binding;
    private PopupMenu popupMenu;
    private HomeAdapter.ListPostMenuListener listener;
    private User user;

    public HomeVH(ItemActivityHomeBinding binding, HomeAdapter.ListPostMenuListener listener, User user) {
        super(binding.getRoot());

        this.user = user;
        this.binding = binding;
        this.listener = listener;
        binding.setEvent(this);

        popupMenu = new PopupMenu(binding.getRoot().getContext(), binding.ibMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_list_post, popupMenu.getMenu());
    }

    public void bind(Post post) {
        binding.setData(post);

        if (user != null) {
            if (user.getName().equals(post.getName())) {
                binding.ibMenu.setVisibility(View.VISIBLE);
            } else {
                binding.ibMenu.setVisibility(View.INVISIBLE);
            }
        }

        Glide.with(binding.getRoot().getContext())
                .load(post.getImage())
                .into(binding.ivImage);

        Glide.with(binding.getRoot().getContext())
                .load(post.getAvatar())
                .asBitmap().centerCrop()
                .into(new BitmapImageViewTarget(binding.ivAvatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(binding.getRoot().getResources(), resource);
                        roundedBitmapDrawable.setCircular(true);
                        binding.ivAvatar.setImageDrawable(roundedBitmapDrawable);
                    }
                });
    }

    @Override
    public void onMenuClick(final Post post) {
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.mn_edit_post:
                        editPost(post);
                        break;
                    case R.id.mn_delete_post:
                        deletePost(post);
                        break;
                }
                return false;
            }


        });
    }

    private void deletePost(Post post) {
        listener.onDeletePost(post);
    }

    private void editPost(Post post) {
        listener.onEditPost(post);
    }
}
