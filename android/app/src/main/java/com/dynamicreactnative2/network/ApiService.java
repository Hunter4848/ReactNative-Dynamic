package com.dynamicreactnative2.network;

import com.dynamicreactnative2.model.EversafeRequest;

import retrofit2.http.Body;
import retrofit2.http.POST;
import com.dynamicreactnative2.model.EverspinResponse;
import retrofit2.Call;
public interface ApiService {

    @POST("user_login_eversafe")
    Call<EverspinResponse> postLogin(@Body EversafeRequest request);
}
