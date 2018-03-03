package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public interface LocationInteractor {
    interface GetLocationListener {
        void getLocation(DatabaseError e, UserLocation location);
    }

    interface PushLocationListener {
        void pushLocation(Exception e);
    }

    interface GetListLocationListener {
        void listLocation(ArrayList<UserLocation> locations, DatabaseError e);
    }

    void pushLocation(UserLocation location, PushLocationListener listener);

    void getLocation(String userId, GetLocationListener listener);

    void getListLocation(GetListLocationListener listener);
}
