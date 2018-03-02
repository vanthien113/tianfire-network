package com.example.thienpro.mvp_firebase.model.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class User {
    private String email;
    private String name;
    private String address;
    private Boolean sex;
    private String avatar;
    private String cover;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User() {
    }

    public User(String email, String name, String address, Boolean sex, String avatar, String cover) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.sex = sex;
        this.avatar = avatar;
        this.cover = cover;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("name", name);
        result.put("address", address);
        result.put("sex", sex);
        result.put("avatar", avatar);
        result.put("cover", cover);
        return result;
    }
}
