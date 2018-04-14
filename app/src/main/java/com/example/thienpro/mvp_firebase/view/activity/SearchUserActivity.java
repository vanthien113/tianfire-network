package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Context;
import android.content.Intent;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivitySearchUserBinding;
import com.example.thienpro.mvp_firebase.view.bases.BaseActivity;

public class SearchUserActivity extends BaseActivity<ActivitySearchUserBinding> {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SearchUserActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_user;
    }

    @Override
    protected void init() {

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
}
