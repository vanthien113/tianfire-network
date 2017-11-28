package com.example.thienpro.mvp_firebase.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.thienpro.mvp_firebase.R;
import com.example.thienpro.mvp_firebase.databinding.ActivityPostBinding;
import com.example.thienpro.mvp_firebase.presenter.Impl.PostPresenterImpl;
import com.example.thienpro.mvp_firebase.presenter.PostPresenter;
import com.example.thienpro.mvp_firebase.view.PostView;

/**
 * Created by ThienPro on 11/28/2017.
 */

public class PostActivity extends AppCompatActivity implements PostView {
    private ActivityPostBinding binding;
    private PostPresenter postPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        postPresenter = new PostPresenterImpl(this);
        binding.setEvent(this);
    }

    @Override
    public void onBackClick() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPostClick() {
        if (TextUtils.isEmpty(binding.etPost.getText()))
            Toast.makeText(this, "Hãy nhập cảm nhận của bạn!", Toast.LENGTH_SHORT).show();
        else {
            postPresenter.newPost(binding.etPost.getText().toString());
            posted();
        }
    }

    @Override
    public void posted() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
