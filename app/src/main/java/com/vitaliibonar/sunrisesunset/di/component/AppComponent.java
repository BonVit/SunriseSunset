package com.vitaliibonar.sunrisesunset.di.component;

import com.vitaliibonar.sunrisesunset.activity.SuggestionsActivity;
import com.vitaliibonar.sunrisesunset.di.module.AppModule;
import com.vitaliibonar.sunrisesunset.di.module.GoogleApiModule;
import com.vitaliibonar.sunrisesunset.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, GoogleApiModule.class, NetworkModule.class})
@Singleton
public interface AppComponent {
    void inject(SuggestionsActivity suggestionsActivity);
}

