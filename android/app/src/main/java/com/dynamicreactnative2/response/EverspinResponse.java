package com.dynamicreactnative2.response;

import com.google.gson.annotations.SerializedName;

public class EverspinResponse {

    @SerializedName("result")
    private boolean isResult;

    @SerializedName("secureStorage")
    private String secureStorage;

    @SerializedName("errorMsg")
    private String errorMsg;

    @SerializedName("payload")
    private String payload;

    // Constructor
    public EverspinResponse(boolean isResult, String secureStorage, String errorMsg, String payload) {
        this.isResult = isResult;
        this.secureStorage = secureStorage;
        this.errorMsg = errorMsg;
        this.payload = payload;
    }

    // Getter and Setter methods
    public boolean isResult() {
        return isResult;
    }

    public void setResult(boolean isResult) {
        this.isResult = isResult;
    }

    public String getSecureStorage() {
        return secureStorage;
    }

    public void setSecureStorage(String secureStorage) {
        this.secureStorage = secureStorage;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
