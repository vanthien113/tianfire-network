package com.example.thienpro.mvp_firebase.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.thienpro.mvp_firebase.R;

/**
 * Created by ThienPro on 11/18/2017.
 */

public class SplashActivity extends Activity {
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.startActivity(SplashActivity.this);
                finish();
            }
        }, 2000);
    }
}
