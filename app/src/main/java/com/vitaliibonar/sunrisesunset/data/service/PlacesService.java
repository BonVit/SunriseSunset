package com.vitaliibonar.sunrisesunset.data.service;

import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.tasks.Task;
import com.vitaliibonar.sunrisesunset.model.PlaceSuggestion;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface PlacesService {

    Disposable getClosestPlace(Task<PlaceLikelihoodBufferResponse> task,
                               Consumer<PlaceLikelihood> onSuccess,
                               Consumer<Throwable> onError);

    Disposable getSuggestions(Task<AutocompletePredictionBufferResponse> task,
                              Consumer<List<PlaceSuggestion>> onSuccess,
                              Consumer<Throwable> onError);

    Disposable getPlaceDetails(Task<PlaceBufferResponse> task,
                                      Consumer<Place> onSuccess,
                                      Consumer<Throwable> onError);

}
