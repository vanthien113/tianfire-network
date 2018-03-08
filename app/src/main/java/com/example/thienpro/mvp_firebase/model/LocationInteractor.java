package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public interface LocationInteractor {
    interface GetLocationCallback {
        void getLocation(DatabaseError e, UserLocation location);
    }

    interface PushLocationCallback {
        void pushLocation(Exception e);
    }

    interface GetListLocationCallback {
        void listLocation(ArrayList<UserLocation> locations, DatabaseError e);
    }

    interface GetShareLocationCallback {
        void getShareLocation(boolean isShare);
    }

    void pushLocation(UserLocation location, PushLocationCallback callback);

    void getLocation(String userId, GetLocationCallback callback);

    void getListLocation(GetListLocationCallback callback);

    void saveShareLocation(boolean isShare);

    void getShareLocation(GetShareLocationCallback callback);
}
