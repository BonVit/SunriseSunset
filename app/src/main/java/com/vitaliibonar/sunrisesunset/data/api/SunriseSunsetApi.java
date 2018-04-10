package com.vitaliibonar.sunrisesunset.data.api;

import com.vitaliibonar.sunrisesunset.model.SunriseSunsetResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SunriseSunsetApi {
    String BASE_URL = "https://api.sunrise-sunset.org/";

    @GET("json")
    Observable<SunriseSunsetResponse> getSunriseSunsetData(@Query("lat") double lat,
                                                           @Query("lng") double lng,
                                                           @Query("date") String date);
}
