package com.example.thienpro.mvp_firebase.ultils.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thienpro.mvp_firebase.R;

/**
 * Created by vanthien113 on 3/15/18.
 */

public class Topbar extends FrameLayout {
    private TextView tvTitle;
    private ImageView ivBack;
    private String title;

    public Topbar(@NonNull Context context) {
        super(context);
    }

    public Topbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public Topbar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    protected void init(@Nullable AttributeSet attributeSet) {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_topbar, this, true);

        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);

        if (attributeSet != null) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.Topbar, 0, 0);
            title = typedArray.getString(R.styleable.Topbar_setTitle);
        }
        if (title != null) {
            tvTitle.setText(title);
        }

        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).onBackPressed();
            }
        });
    }

    public ImageView getImageBack() {
        return ivBack;
    }

    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }
}
