package com.vitaliibonar.sunrisesunset.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GoogleApiModule {

    @Provides
    @NonNull
    @Singleton
    GeoDataClient provideGeoDataClient(Context context) {
        return Places.getGeoDataClient(context);
    }

    @Provides
    @NonNull
    @Singleton
    PlaceDetectionClient providePlaceDetectionClient(Context context) {
        return Places.getPlaceDetectionClient(context);
    }

}
