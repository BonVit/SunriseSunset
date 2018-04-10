package com.vitaliibonar.sunrisesunset.di.module;

import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.vitaliibonar.sunrisesunset.data.api.SunriseSunsetApi;
import com.vitaliibonar.sunrisesunset.data.service.PlacesService;
import com.vitaliibonar.sunrisesunset.data.service.SunriseSunsetService;
import com.vitaliibonar.sunrisesunset.data.service.impl.PlacesServiceImpl;
import com.vitaliibonar.sunrisesunset.data.service.impl.SunriseSunsetServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final int TIMEOUT = 5000;

    @Provides
    @NonNull
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(SunriseSunsetApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    SunriseSunsetApi provideSunriseSunsetApi(Retrofit retrofit) {
        return retrofit.create(SunriseSunsetApi.class);
    }

    @Provides
    @NonNull
    @Singleton
    PlacesService providePlacesService() {
        return new PlacesServiceImpl(TIMEOUT);
    }

    @Provides
    @NonNull
    @Singleton
    SunriseSunsetService provideSunriseSunsetService(SunriseSunsetApi api) {
        return new SunriseSunsetServiceImpl(api);
    }


}
