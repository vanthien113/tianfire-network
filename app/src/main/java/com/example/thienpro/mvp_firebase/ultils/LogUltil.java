package com.example.thienpro.mvp_firebase.ultils;

import android.util.Log;

public class LogUltil {
    public static void log(Class className, Object object) {
        Log.e("THIEN", className.getSimpleName() + ": " + object.toString());
    }
}
