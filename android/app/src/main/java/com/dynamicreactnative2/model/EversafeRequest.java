package com.dynamicreactnative2.model;

import com.google.gson.annotations.SerializedName;

public class EversafeRequest {
    
    @SerializedName("payload")
    private String payload;
    
    @SerializedName("evToken")
    private String evToken;
    
    @SerializedName("evEncDesc")
    private String evEncDesc;

    public EversafeRequest(String payload, String evToken, String evEncDesc) {
        this.payload = payload;
        this.evToken = evToken;
        this.evEncDesc = evEncDesc;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getEvToken() {
        return evToken;
    }

    public void setEvToken(String evToken) {
        this.evToken = evToken;
    }

    public String getEvEncDesc() {
        return evEncDesc;
    }

    public void setEvEncDesc(String evEncDesc) {
        this.evEncDesc = evEncDesc;
    }
}
