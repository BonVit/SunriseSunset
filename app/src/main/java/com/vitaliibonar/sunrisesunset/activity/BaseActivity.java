package com.vitaliibonar.sunrisesunset.activity;

import android.app.ProgressDialog;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vitaliibonar.sunrisesunset.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private List<Disposable> disposables = new ArrayList<>();

    @LayoutRes
    protected abstract int getLayoutRes();

    protected void showProgressDialog() {
        hideProgressDialog();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressDialog();
        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        disposables.clear();
    }

    protected Disposable bind(Disposable disposable) {
        disposables.add(disposable);
        return disposable;
    }
}
