package com.example.thienpro.mvp_firebase.view;

import android.widget.Toast;

import com.example.thienpro.mvp_firebase.model.entity.Post;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

/**
 * Created by ThienPro on 11/10/2017.
 */

public interface HomeView {
    void showAllPost(ArrayList<Post> list);

    void loadPostError(DatabaseError e);
}
