package com.example.thienpro.mvp_firebase;

import android.app.Application;

import com.example.thienpro.mvp_firebase.di.component.AppComponent;
import com.example.thienpro.mvp_firebase.di.component.DaggerAppComponent;
import com.example.thienpro.mvp_firebase.ultils.LogUltil;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class ProjectApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        getAppComponent().inject(this);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        LogUltil.log(getClass(), refreshedToken);
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
