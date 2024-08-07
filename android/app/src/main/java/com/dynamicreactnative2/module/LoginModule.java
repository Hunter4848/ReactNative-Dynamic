package com.dynamicreactnative2.module;

import android.content.Intent;
import android.util.Log;

import com.dynamicreactnative2.DetailUserActivity;
import com.dynamicreactnative2.model.MEncryptedRequest;
import com.dynamicreactnative2.model.UserDetail;
import com.dynamicreactnative2.network.ApiConfig;
import com.dynamicreactnative2.response.EverspinResponse;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;

import org.json.JSONObject;

import kr.co.everspin.eversafe.EversafeHelper;
import kr.co.everspin.eversafe.components.base64.Base64;
import retrofit2.Call;
import retrofit2.Response;


public class LoginModule extends ReactContextBaseJavaModule {

    private static ReactApplicationContext reactContext;

    public LoginModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @Override
    public String getName() {
        return "LoginModule";
    }

    @ReactMethod
    public void login(String username, String password, Callback callback) {
        // Implementasi kode login dari fungsi `login()` di sini
        // Contoh:
        EversafeHelper.getInstance().getEncryptionContext(200, encryptionContext -> {
            try {
                JSONObject json = new JSONObject();
                json.put("user_name", username);
                json.put("user_password", password);
                byte[] bytes = json.toString().getBytes();
                String payload = Base64.encodeBase64String(encryptionContext.encrypt(bytes));
                Log.d("payload", payload.toString());
                String evToken = Base64.encodeBase64String(encryptionContext.encrypt(encryptionContext.getVerificationToken()));
                Log.d("evToken", evToken.toString());
                String evEncDesc = Base64.encodeBase64String(encryptionContext.getContextDescriptor());
                Log.d("evEncDesc", evEncDesc.toString());

                MEncryptedRequest encryptedRequest = new MEncryptedRequest(payload, evToken, evEncDesc);
                Log.d("encryptedRequest", encryptedRequest.toString());

                ApiConfig.getLoginInstance().postLogin(encryptedRequest).enqueue(new retrofit2.Callback<EverspinResponse>() {
                    @Override
                    public void onResponse(Call<EverspinResponse> call, Response<EverspinResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                byte[] bytePayload = Base64.decodeBase64(response.body().getPayload());
                                byte[] byteDecrypt = encryptionContext.decrypt(bytePayload);
                                if (byteDecrypt != null) {
                                    String decryptPayload = new String(byteDecrypt, java.nio.charset.StandardCharsets.UTF_8);
                                    UserDetail userDetail = new Gson().fromJson(decryptPayload, UserDetail.class);
                                    if (userDetail != null) {
                                        Intent intent = new Intent(reactContext, DetailUserActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        reactContext.startActivity(intent);
                                        callback.invoke(null, "Login successful");
                                    } else {
                                        callback.invoke("Error: User detail is null", null);
                                    }
                                } else {
                                    Log.d("Error: Decryption failed", encryptedRequest.toString());
                                    callback.invoke("Error: Decryption failed", null);
                                }
                            } catch (Exception e) {
                                callback.invoke("Error: " + e.getMessage(), null);
                            }
                        } else {
                            callback.invoke("Error: Response unsuccessful", null);
                        }
                    }

                    @Override
                    public void onFailure(Call<EverspinResponse> call, Throwable t) {
                        callback.invoke("Error: " + t.getMessage(), null);
                        Log.e("login", t.toString());
                    }
                });
            } catch (Exception e) {
                callback.invoke("Error: " + e.getMessage(), null);
            }
        });
    }
}