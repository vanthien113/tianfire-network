package com.example.thienpro.mvp_firebase.services;

import com.example.thienpro.mvp_firebase.ultils.LogUltil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        LogUltil.log(getClass(), remoteMessage.getNotification().getBody());
    }
}
