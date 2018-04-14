package com.example.thienpro.mvp_firebase.view.adapters;

import android.databinding.ViewDataBinding;
import android.support.v4.content.ContextCompat;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ItemActivityHomeBinding;
import com.example.thienpro.mvp_firebase.databinding.ItemProfileHeaderBinding;
import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.SHStringHelper;
import com.example.thienpro.mvp_firebase.ultils.widget.SHBitmapHelper;
import com.example.thienpro.mvp_firebase.view.bases.AbsBindingAdapter;

import java.util.ArrayList;

public class ProfileAdapter extends AbsBindingAdapter<ViewDataBinding> {
    private final int HEADER = 0;
    private final int POST = 1;

    private User user;
    private ArrayList<Post> mLisPost;

    public ProfileAdapter(ArrayList<Post> mLisPost, User user) {
        super(null);
        this.mLisPost = mLisPost;
        this.user = user;
    }

    @Override
    protected int getLayoutResourceId(int viewType) {
        switch (viewType) {
            case HEADER:
                return R.layout.item_profile_header;
            default:
                return R.layout.item_activity_home;
        }
    }

    @Override
    public void updateBinding(ViewDataBinding binding, int position) {
        if (binding instanceof ItemProfileHeaderBinding) {
            setItemHeaderData((ItemProfileHeaderBinding) binding);
        } else if (binding instanceof ItemActivityHomeBinding) {
            setItemHomeData((ItemActivityHomeBinding) binding, position);
        }
    }

    private void setItemHeaderData(ItemProfileHeaderBinding binding) {
        binding.setData(user);
        SHBitmapHelper.bindImage(binding.ivAvatar, user.getAvatar());
        SHBitmapHelper.bindImage(binding.ivCover, user.getCover());
    }

    private void setItemHomeData(ItemActivityHomeBinding binding, int position) {
        binding.setData(mLisPost.get(position));

        SHBitmapHelper.bindImage(binding.ivAvatar, mLisPost.get(position).getAvatar());
        SHBitmapHelper.bindImage(binding.ivImage, mLisPost.get(position).getImage());
        SHStringHelper.hashTag(mLisPost.get(position).getPost(), ContextCompat.getColor(binding.getRoot().getContext(), R.color.colorGreen), binding.tvPost);
    }

    @Override
    public int getItemCount() {
        return mLisPost == null ? 1 : mLisPost.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case HEADER:
                return HEADER;
            default:
                return POST;
        }
    }
}
