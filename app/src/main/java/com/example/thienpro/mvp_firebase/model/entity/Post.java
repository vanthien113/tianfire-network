package com.example.thienpro.mvp_firebase.model.entity;

/**
 * Created by ThienPro on 11/11/2017.
 */

public class Post {
    private String id;
    private String name;
    private String timePost;
    private String post;
    private String image;

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

    public Post(String id, String name, String timePost, String post, String image) {
        this.id = id;
        this.name = name;
        this.timePost = timePost;
        this.post = post;
        this.image = image;
    }
}
