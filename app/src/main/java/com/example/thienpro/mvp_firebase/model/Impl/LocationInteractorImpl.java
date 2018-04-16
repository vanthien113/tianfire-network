package com.example.thienpro.mvp_firebase.model.Impl;

import android.support.annotation.NonNull;

import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.example.thienpro.mvp_firebase.ultils.SharedPreferencesUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocationInteractorImpl implements LocationInteractor {
    private static final String LOCATION = "locations";

    private static final String LNG = "lng";
    private static final String LAT = "lat";
    private static final String STATUS = "status";
    private static final String NAME = "name";
    private static final String ID = "id";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<UserLocation> listLocation;

    public LocationInteractorImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        listLocation = new ArrayList<>();
    }

    public void pushLocation(UserLocation location, final PushLocationCallback callback) {
        String userId = user.getUid();

        location.setUserName(location.getUserName());
        location.setUserId(userId);

        Map<String, Object> postValues = location.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/locations/" + userId, postValues);

        mDatabase.updateChildren(childUpdates).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.pushLocation(e);
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.pushLocation(null);
            }
        });

    }

    @Override
    public void getLocation(String userId, final GetLocationCallback callback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                double lng = (double) map.get(LNG);
                double lat = (double) map.get(LAT);
                boolean status = (boolean) map.get(STATUS);
                String userName = (String) map.get(NAME);
                String userId = (String) map.get(ID);

                UserLocation location = new UserLocation(userName, userId, lng, lat, status);

                callback.getLocation(null, location);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.getLocation(databaseError, null);
            }
        };

        mDatabase.child(LOCATION).child(userId).addValueEventListener(valueEventListener);
    }

    @Override
    public void getListLocation(final GetListLocationCallback callback) {
        mDatabase.child(LOCATION).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) dsp.getValue();
                    double lng = (double) map.get(LNG);
                    double lat = (double) map.get(LAT);
                    boolean status = (boolean) map.get(STATUS);
                    String userName = (String) map.get(NAME);
                    String userId = (String) map.get(ID);

                    if (status) {
                        UserLocation location = new UserLocation(userName, userId, lng, lat, status);
                        listLocation.add(location);
                    }
                }

                callback.listLocation(listLocation, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.listLocation(null, databaseError);
            }
        });
    }
}
