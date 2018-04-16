package com.example.thienpro.mvp_firebase.view.adapters.viewholder;

import android.support.v7.widget.RecyclerView;

import com.example.thienpro.mvp_firebase.databinding.ItemSearchUserBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.ItemSearchUserView;
import com.example.thienpro.mvp_firebase.view.adapters.SearchUserAdapter;

public class SearchUserVH extends RecyclerView.ViewHolder implements ItemSearchUserView {
    private ItemSearchUserBinding binding;
    private SearchUserAdapter.SearchUserClickListener listener;

    public SearchUserVH(ItemSearchUserBinding binding, SearchUserAdapter.SearchUserClickListener listener) {
        super(binding.getRoot());
        this.listener = listener;
        this.binding = binding;
        binding.setEvent(this);
    }

    public void bind(User user) {
        SHBitmapHelper.bindImage(binding.ivAvatar, user.getAvatar());
        binding.setData(user);
    }

    @Override
    public void onUserClick(User user) {
        listener.userClick(user);
    }
}
