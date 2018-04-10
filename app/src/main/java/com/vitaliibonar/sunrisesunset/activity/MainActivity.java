package com.vitaliibonar.sunrisesunset.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.tasks.Task;
import com.vitaliibonar.sunrisesunset.App;
import com.vitaliibonar.sunrisesunset.R;
import com.vitaliibonar.sunrisesunset.data.service.PlacesService;
import com.vitaliibonar.sunrisesunset.data.service.SunriseSunsetService;
import com.vitaliibonar.sunrisesunset.databinding.ActivityMainBinding;
import com.vitaliibonar.sunrisesunset.model.SunriseSunsetResponse;
import com.vitaliibonar.sunrisesunset.util.PermissionUtil;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    public static final int REQUEST_CODE_PLACE_ID = 393;

    @Inject
    GeoDataClient geoDataClient;
    @Inject
    PlaceDetectionClient placeDetectionClient;
    @Inject
    PlacesService placesService;
    @Inject
    SunriseSunsetService sunriseSunsetService;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, getLayoutRes());

        binding.buttonPickPlace.setOnClickListener(onPickPlaceClickListener);
        binding.buttonMyPlace.setOnClickListener(onGetCurrentPlaceListener);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PLACE_ID && resultCode == RESULT_OK) {
            getPlaceDetails(SuggestionsActivity.getExtraPlaceId(data));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtil.checkLocationPermission(this)) {
            getCurrentPlace();
        }
    }

    private void getCurrentPlace() {
        if (PermissionUtil.checkLocationPermission(this)) {
            Task<PlaceLikelihoodBufferResponse> task = placeDetectionClient.getCurrentPlace(null);
            showProgressDialog();
            bind(placesService.getClosestPlace(task, this::onGetPlaceSuccess, this::onError));
        } else {
            PermissionUtil.requestLocationPermission(this);
        }
    }

    private void getPlaceDetails(String id) {
        showProgressDialog();
        Task<PlaceBufferResponse> task = geoDataClient.getPlaceById(id);
        bind(placesService.getPlaceDetails(task, this::onGetPlaceSuccess, this::onError));
    }

    private void onGetPlaceSuccess(Place place) {
        getSunriseSunsetData(place);
    }

    private void onError(Throwable throwable) {
        hideProgressDialog();
        showToast(throwable.getLocalizedMessage());
    }

    private void getSunriseSunsetData(Place place) {
        bind(sunriseSunsetService.getPlaceData(place, this::onGetSunriseSunsetSuccess, this::onError));
    }

    private void onGetSunriseSunsetSuccess(SunriseSunsetResponse response) {
        hideProgressDialog();
        binding.tvLocation.setText(getString(R.string.location, response.getResult().getName()));
        binding.tvSunriseTime.setText(getString(R.string.sunrise_time, response.getResult().getSunrise()));
        binding.tvSunsetTime.setText(getString(R.string.sunset_time, response.getResult().getSunset()));
        binding.placeLayout.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener onPickPlaceClickListener = v -> startActivityForResult(SuggestionsActivity.makeIntent(MainActivity.this),
            REQUEST_CODE_PLACE_ID);

    private View.OnClickListener onGetCurrentPlaceListener = v -> getCurrentPlace();
}
