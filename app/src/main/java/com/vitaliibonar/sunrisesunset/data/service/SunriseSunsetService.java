package com.vitaliibonar.sunrisesunset.data.service;

import com.google.android.gms.location.places.Place;
import com.vitaliibonar.sunrisesunset.model.SunriseSunsetResponse;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface SunriseSunsetService {

    Disposable getPlaceData(Place place, Consumer<SunriseSunsetResponse> onSuccess, Consumer<Throwable> onError);

}
