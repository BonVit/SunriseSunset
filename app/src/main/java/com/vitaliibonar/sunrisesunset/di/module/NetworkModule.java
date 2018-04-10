package com.vitaliibonar.sunrisesunset.di.module;

import android.support.annotation.NonNull;

import com.vitaliibonar.sunrisesunset.data.service.PlacesService;
import com.vitaliibonar.sunrisesunset.data.service.impl.PlacesServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    private static final int TIMEOUT = 5000;

    @Provides
    @NonNull
    @Singleton
    PlacesService providePlacesService() {
        return new PlacesServiceImpl(TIMEOUT);
    }

}
