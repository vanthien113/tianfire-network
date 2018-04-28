package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivitySearchUserBinding;
import com.example.thienpro.mvp_firebase.model.entity.User;
import com.example.thienpro.mvp_firebase.presenter.SearchUserPresenter;
import com.example.thienpro.mvp_firebase.ultils.LayoutUltils;
import com.example.thienpro.mvp_firebase.view.SearchUserView;
import com.example.thienpro.mvp_firebase.view.adapters.SearchUserAdapter;
import com.example.thienpro.mvp_firebase.bases.BaseActivity;

import java.util.ArrayList;

public class SearchUserActivity extends BaseActivity<ActivitySearchUserBinding> implements SearchUserView, SearchUserAdapter.SearchUserClickListener {
    private SearchUserPresenter presenter;
    private SearchUserAdapter adapter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SearchUserActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_user;
    }

    @Override
    protected void init() {
        presenter = getAppComponent().getCommonComponent().getSearchUserPresenter();
        presenter.attachView(this);

        getBinding().etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.search(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void startScreen() {

    }

    @Override
    protected void resumeScreen() {

    }

    @Override
    protected void pauseScreen() {

    }

    @Override
    protected void destroyScreen() {

    }

    @Override
    public void showUserSearched(ArrayList<User> userList) {
        adapter = new SearchUserAdapter(userList, this);

        getBinding().rvSearch.setLayoutManager(LayoutUltils.getLinearLayoutManager(this));
        getBinding().rvSearch.setAdapter(adapter);
    }

    @Override
    public void userClick(User user) {
        FriendProfileActivity.startActivity(this, user.getId());
    }
}
