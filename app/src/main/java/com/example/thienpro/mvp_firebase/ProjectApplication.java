package com.example.thienpro.mvp_firebase;

import android.app.Application;

import com.example.thienpro.mvp_firebase.di.component.AppComponent;
import com.example.thienpro.mvp_firebase.di.component.DaggerAppComponent;
import com.google.firebase.database.FirebaseDatabase;

public class ProjectApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        getAppComponent().inject(this);
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .context(this)
                    .build();
        }
        return appComponent;
    }
}
