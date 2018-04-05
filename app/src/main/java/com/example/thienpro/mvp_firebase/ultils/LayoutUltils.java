package com.example.thienpro.mvp_firebase.ultils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class LayoutUltils {
    public static RecyclerView.LayoutParams getRecyclerViewLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayoutManager getLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context, OrientationHelper.VERTICAL, false);
    }
}
