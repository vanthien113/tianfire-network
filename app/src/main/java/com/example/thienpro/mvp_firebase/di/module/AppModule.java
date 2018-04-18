package com.example.thienpro.mvp_firebase.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.thienpro.mvp_firebase.manager.PostManager;
import com.example.thienpro.mvp_firebase.manager.UserManager;
import com.example.thienpro.mvp_firebase.manager.impl.LocalPostManager;
import com.example.thienpro.mvp_firebase.manager.impl.LocalUserManager;
import com.example.thienpro.mvp_firebase.model.Impl.LocationInteractorImpl;
import com.example.thienpro.mvp_firebase.model.Impl.PostInteractorImpl;
import com.example.thienpro.mvp_firebase.model.Impl.UserInteractorImpl;
import com.example.thienpro.mvp_firebase.model.LocationInteractor;
import com.example.thienpro.mvp_firebase.model.PostInteractor;
import com.example.thienpro.mvp_firebase.model.UserInteractor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private static String DATA = "DATA";

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences(DATA, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public UserManager providesUserManager(Gson gson, SharedPreferences sharedPreferences) {
        return new LocalUserManager(gson, sharedPreferences);
    }

    @Provides
    @Singleton
    public UserInteractor providesUserInteractora() {
        return new UserInteractorImpl();
    }

    @Provides
    @Singleton
    public PostInteractor providesPostInteractor() {
        return new PostInteractorImpl();
    }

    @Provides
    @Singleton
    public LocationInteractor providesLocationInteractor() {
        return new LocationInteractorImpl();
    }

    @Provides
    @Singleton
    public PostManager providesPostManager() {
        return new LocalPostManager();
    }

}
