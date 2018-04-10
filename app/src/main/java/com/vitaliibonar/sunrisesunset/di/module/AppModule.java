package com.vitaliibonar.sunrisesunset.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @NonNull
    @Singleton
    Context provideContext() {
        return context;
    }

}
