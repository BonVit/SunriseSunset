package com.vitaliibonar.sunrisesunset.data.service.impl;

import com.google.android.gms.location.places.Place;
import com.vitaliibonar.sunrisesunset.data.api.SunriseSunsetApi;
import com.vitaliibonar.sunrisesunset.data.service.SunriseSunsetService;
import com.vitaliibonar.sunrisesunset.model.SunriseSunsetResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SunriseSunsetServiceImpl implements SunriseSunsetService {

    private SunriseSunsetApi api;

    public SunriseSunsetServiceImpl(SunriseSunsetApi api) {
        this.api = api;
    }

    @Override
    public Disposable getPlaceData(Place place, Consumer<SunriseSunsetResponse> onSuccess, Consumer<Throwable> onError) {
        return api.getSunriseSunsetData(place.getLatLng().latitude,
                place.getLatLng().longitude, "today")
                .subscribeOn(Schedulers.io())
                .doOnNext(response -> response.getResult().setName(place.getName().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
    }
}
