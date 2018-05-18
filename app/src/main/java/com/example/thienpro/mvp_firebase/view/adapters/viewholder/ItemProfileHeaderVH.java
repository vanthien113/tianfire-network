package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.support.v7.widget.RecyclerView;

import com.example.thienpro.mvp_firebase.databinding.ItemProfileHeaderBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.ItemProfileHeaderView;
import com.example.thienpro.mvp_firebase.view.adapters.ProfileAdapter;

public class ItemProfileHeaderVH extends RecyclerView.ViewHolder implements ItemProfileHeaderView {
    protected ItemProfileHeaderBinding binding;
    private ProfileAdapter.ItemProfileClickListener listener;
    private User user;

    public ItemProfileHeaderVH(ItemProfileHeaderBinding binding, ProfileAdapter.ItemProfileClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public void bind(User user) {
        binding.setData(user);
        binding.setEvent(this);
        this.user = user;

        SHBitmapHelper.bindImage(binding.ivAvatar, user.getAvatar());
        SHBitmapHelper.bindImage(binding.ivCover, user.getCover());
    }

    @Override
    public void onChangeAvatar() {
        listener.onChangeAvatar();
    }

    @Override
    public void onChangeCover() {
        listener.onChangeCover();
    }

    @Override
    public void onShowListPictureClick() {
        listener.onShowListPictureClick();
    }

    @Override
    public void onPost() {
        listener.onPost();
    }

    @Override
    public void onAvatarClick() {
        listener.onAvatarClick(user.getAvatar());
    }

    @Override
    public void onCoverClick() {
        listener.onCoverClick(user.getCover());
    }
}
