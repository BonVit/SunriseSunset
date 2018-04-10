package com.vitaliibonar.sunrisesunset.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SunriseSunsetResponse {
    @SerializedName("results")
    @Expose
    private SunriseSunsetResult result;
    @SerializedName("status")
    @Expose
    private String status;

    public SunriseSunsetResult getResult() {
        return result;
    }

    public void setResult(SunriseSunsetResult result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
