package com.example.thienpro.mvp_firebase.model.entity;

import android.annotation.SuppressLint;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThienPro on 11/11/2017.
 */

public class Post implements Serializable{
    private String id;
    private String name;
    private String timePost;
    private String post;
    private String image;
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimePost() {
        return timePost;
    }

    public void setTimePost(String timePost) {
        this.timePost = timePost;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Post(String id, String name, String timePost, String post, String image, String avatar) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.image = image;
        this.avatar = avatar;
        this.timePost = timePost;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("post", post);
        result.put("image", image);
        result.put("avatar", avatar);
        result.put("timePost", timePost);
        return result;
    }
}
