package com.vitaliibonar.sunrisesunset;

import android.app.Application;

import com.vitaliibonar.sunrisesunset.di.component.AppComponent;
import com.vitaliibonar.sunrisesunset.di.component.DaggerAppComponent;
import com.vitaliibonar.sunrisesunset.di.module.AppModule;
import com.vitaliibonar.sunrisesunset.di.module.GoogleApiModule;
import com.vitaliibonar.sunrisesunset.di.module.NetworkModule;

public class App extends Application {
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .googleApiModule(new GoogleApiModule())
                .networkModule(new NetworkModule())
                .build();
    }

}
