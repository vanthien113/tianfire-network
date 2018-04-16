package com.example.thienpro.mvp_firebase.di.component;

import android.content.Context;

import com.example.thienpro.mvp_firebase.ProjectApplication;
import com.example.thienpro.mvp_firebase.di.module.AppModule;
import com.example.thienpro.mvp_firebase.di.module.CommonModule;
import com.example.thienpro.mvp_firebase.manager.LocationManager;
import com.example.thienpro.mvp_firebase.manager.PostManager;
import com.example.thienpro.mvp_firebase.manager.UserManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, CommonModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);

        AppComponent build();
    }

    void inject(ProjectApplication projectApplication);

    UserManager getUserManager();

    CommonComponent getCommonComponent();

    PostManager getPostManager();

    LocationManager getLocationManager();
}
