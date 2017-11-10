package com.example.thienpro.mvp_firebase.model.entity;

/**
 * Created by ThienPro on 11/10/2017.
 */

public class User {
    private String email;
    private String name;
    private String address;
    private Boolean sex;

    public User() {
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

    public User(String email, String name, String address, Boolean sex) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.sex = sex;
    }
}
