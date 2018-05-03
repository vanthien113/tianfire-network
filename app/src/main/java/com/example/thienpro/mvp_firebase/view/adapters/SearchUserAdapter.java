package com.example.thienpro.mvp_firebase.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.thienpro.mvp_firebase.databinding.ItemSearchUserBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.adapters.viewholder.SearchUserVH;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserVH> {
    private List<User> list;
    private SearchUserClickListener listener;

    public SearchUserAdapter(SearchUserClickListener listener) {
        this.listener = listener;
    }

    public void updateAdapter(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public SearchUserVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSearchUserBinding binding = ItemSearchUserBinding.inflate(LayoutInflater.from(parent.getContext()));
        binding.getRoot().setLayoutParams(LayoutUltils.getRecyclerViewLayoutParams());
        return new SearchUserVH(binding, listener);
    }

    @Override
    public void onBindViewHolder(SearchUserVH holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public interface SearchUserClickListener {
        void userClick(User user);
    }
}
