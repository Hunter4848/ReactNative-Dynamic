package com.dynamicreactnative2.network;


import kr.co.everspin.eversafe.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class ApiConfig {
    private static final Retrofit retrofit;
    private static final ApiService loginInstance;

    static {
        // Configuring OkHttpClient




        // Configuring Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.8.12.37:9002/everspin/") // Corrected URL (fixed typo)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Creating ApiService instance
        loginInstance = retrofit.create(ApiService.class);
    }

    // Method to get ApiService instance
    public static ApiService getLoginInstance() {
        return loginInstance;
    }
}

