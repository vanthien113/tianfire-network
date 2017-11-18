package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.User;

import java.util.HashMap;
import java.util.Map;

/*
*- Lớp M: xử lý dữ liệu -> Trả dữ liệu về P thông qua callback
* */

/**
 * Created by ThienPro on 11/10/2017.
 */

public class UserInteractor {
    private LoadUserListener loadUserListener;

    public Map<String, Object> toMap(User user) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", user.getEmail());
        result.put("name", user.getName());
        result.put("address", user.getAddress());
        result.put("sex", user.getSex());
        return result;
    }
}
