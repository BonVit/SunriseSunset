package com.vitaliibonar.sunrisesunset.data.service.impl;

import android.annotation.SuppressLint;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.tasks.Task;
import com.vitaliibonar.sunrisesunset.data.service.PlacesService;
import com.vitaliibonar.sunrisesunset.model.PlaceSuggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlacesServiceImpl implements PlacesService {

    private long timeout;

    public PlacesServiceImpl(long timeout) {
        this.timeout = timeout;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public Disposable getClosestPlace(Task<PlaceLikelihoodBufferResponse> task,
                                      Consumer<PlaceLikelihood> onSuccess,
                                      Consumer<Throwable> onError) {
        return Observable.create((ObservableOnSubscribe<PlaceLikelihood>) emitter -> {
            try {
                task.addOnCompleteListener(task1 -> emitter.onNext(task1.getResult().get(0)));
                Thread.sleep(timeout);
                if (!task.isComplete() || !task.isSuccessful()) {
                    throw new TimeoutException();
                }
            } catch (Exception e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(new Throwable(e));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public Disposable getPlaceDetails(Task<PlaceBufferResponse> task,
                                      Consumer<Place> onSuccess,
                                      Consumer<Throwable> onError) {
        return Observable.create((ObservableOnSubscribe<Place>) emitter -> {
            try {
                task.addOnCompleteListener(task1 -> emitter.onNext(task1.getResult().get(0)));
                Thread.sleep(timeout);
                if (!task.isComplete() || !task.isSuccessful()) {
                    throw new TimeoutException();
                }
            } catch (Exception e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(new Throwable(e));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
    }

    @Override
    public Disposable getSuggestions(Task<AutocompletePredictionBufferResponse> task,
                                     Consumer<List<PlaceSuggestion>> onSuccess,
                                     Consumer<Throwable> onError) {
        return Observable.create((ObservableOnSubscribe<List<PlaceSuggestion>>) emitter -> {
            try {
                task.addOnCompleteListener(task1 -> {
                    List<PlaceSuggestion> placeSuggestions = new ArrayList<>();
                    for (AutocompletePrediction prediction : task1.getResult()) {
                        placeSuggestions.add(new PlaceSuggestion(prediction.getPlaceId(),
                                prediction.getPrimaryText(null).toString()));
                    }
                    emitter.onNext(placeSuggestions);
                });
                Thread.sleep(timeout);
                if (!task.isComplete() || !task.isSuccessful()) {
                    throw new TimeoutException();
                }
            } catch (Exception e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(new Throwable(e));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
    }
}
