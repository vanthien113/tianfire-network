package com.example.thienpro.mvp_firebase.model;

import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.google.firebase.database.DatabaseError;

public interface LocationInteractor {
    interface GetLocationListener {
        void getLocation(DatabaseError e, UserLocation location);
    }

    interface PushLocationListener {
        void pushLocation(Exception e);
    }

    void pushLocation(UserLocation location, PushLocationListener listener);

    void getLocation(GetLocationListener listener);
}
