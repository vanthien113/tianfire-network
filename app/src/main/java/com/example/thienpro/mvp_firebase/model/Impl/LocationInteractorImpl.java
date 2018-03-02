package com.example.thienpro.mvp_firebase.model.Impl;

import android.support.annotation.NonNull;

import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.entity.UserLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LocationInteractorImpl implements LocationInteractor {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public LocationInteractorImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void pushLocation(UserLocation location, final PushLocationListener listener) {
        String userId = user.getUid();

        location.setUserName("dsdsd");
        location.setUserId(userId);

        Map<String, Object> postValues = location.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/locations/" + userId, postValues);

        mDatabase.updateChildren(childUpdates).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.pushLocation(e);
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.pushLocation(null);
            }
        });

    }

    @Override
    public void getLocation(final GetLocationListener listener) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String lng = (String) map.get("lng");
                String lat = (String) map.get("lat");
                String status = (String) map.get("status");
                String userName = (String) map.get("name");
                String userId = (String) map.get("id");

                UserLocation location = new UserLocation(userName, userId, Double.parseDouble(lng), Double.parseDouble(lat), Boolean.parseBoolean(status));

                listener.getLocation(null, location);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.getLocation(databaseError, null);
            }
        };

        mDatabase.child("location").child(user.getUid()).addValueEventListener(valueEventListener);
    }
}
