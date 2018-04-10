package com.vitaliibonar.sunrisesunset.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.tasks.Task;
import com.vitaliibonar.sunrisesunset.App;
import com.vitaliibonar.sunrisesunset.R;
import com.vitaliibonar.sunrisesunset.adapter.SuggestionsAdapter;
import com.vitaliibonar.sunrisesunset.data.service.PlacesService;
import com.vitaliibonar.sunrisesunset.databinding.ActivitySuggestionsBinding;
import com.vitaliibonar.sunrisesunset.model.PlaceSuggestion;
import com.vitaliibonar.sunrisesunset.util.RxUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SuggestionsActivity extends BaseActivity {

    private static final int DEBOUNCE_INPUT = 200;
    private static final String EXTRA_PLACE_ID = "extra_place_id";

    @Inject
    PlacesService placesService;
    @Inject
    GeoDataClient geoDataClient;

    private ActivitySuggestionsBinding binding;
    private Disposable suggestionsDisposable;

    private SuggestionsAdapter suggestionsAdapter;

    public Intent makeIntent(Context context) {
        return new Intent(context, SuggestionsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent().inject(this);

        binding = DataBindingUtil.setContentView(this, getLayoutRes());

        initSuggestionsRecyclerView();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_suggestions;
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressDialog();
    }

    private void subscribe() {
        bind(RxUtil.getTextWatcherObservable(binding.etCity)
                .debounce(DEBOUNCE_INPUT, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::fetchSuggestions));
    }

    private void initSuggestionsRecyclerView() {
        suggestionsAdapter = new SuggestionsAdapter();
        suggestionsAdapter.setItemClickedCallback(suggestionClickedCallback);
        binding.rvSuggestions.setAdapter(suggestionsAdapter);
    }

    private void fetchSuggestions(String input) {
        if (suggestionsDisposable != null && !suggestionsDisposable.isDisposed()) {
            suggestionsDisposable.dispose();
        }
        Task<AutocompletePredictionBufferResponse> task = geoDataClient.getAutocompletePredictions(input,
                null,
                null);
        suggestionsDisposable = bind(placesService.getSuggestions(task, this::onSuggestionsSuccess, this::onError));
    }

    private void onSuggestionsSuccess(List<PlaceSuggestion> suggestions) {
        suggestionsAdapter.setSuggestions(suggestions);
    }

    private void onError(Throwable throwable) {
        showToast(throwable.getLocalizedMessage());
    }

    private SuggestionsAdapter.ItemClickedCallback suggestionClickedCallback = id -> {
        setResult(RESULT_OK, resultIntent(id));
        finish();
    };

    private Intent resultIntent(String id) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PLACE_ID, id);
        return intent;
    }
}
