package com.dynamicreactnative2.module;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.nio.charset.StandardCharsets;

import kr.co.everspin.eversafe.EncryptionContext;
import kr.co.everspin.eversafe.EncryptionContextAction;
import kr.co.everspin.eversafe.EversafeHelper;
import kr.co.everspin.eversafe.components.base64.Base64;


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
        new EversafeHelper.GetVerificationTokenTask() {
            @Override
            public void onAction(byte[] bytes, String verificationTokenAsBase64, int result) {
                Log.d("verification", "verificationTokenAsBase64: " + verificationTokenAsBase64);
                callback.invoke(null, verificationTokenAsBase64);
            }
        }.setTimeout(10000).execute();
    }


    /*@ReactMethod
    public void login(String username, String password, Callback callback) {
        new EversafeHelper.GetVerificationTokenTask() {
            @Override
            public void onAction(byte[] bytes, String verificationTokenAsBase64, int result) {
                Log.d("verification", "verificationTokenAsBase64: " + verificationTokenAsBase64);

                EversafeHelper.getInstance().getEncryptionContext(200, encryptionContext -> {
                    try {
                        JSONObject json = new JSONObject();
                        json.put("user_name", username);
                        json.put("user_password", password);

                        byte[] bytes1 = json.toString().getBytes();

                        String payload = Base64.encodeBase64String(encryptionContext.encrypt(bytes1));
                        Log.d("payload", payload);

                        String evToken = kr.co.everspin.eversafe.components.base64.Base64.encodeBase64String(encryptionContext.encrypt(encryptionContext.getVerificationToken()));
                        Log.d("evToken", evToken);

                        String evEncDesc = kr.co.everspin.eversafe.components.base64.Base64.encodeBase64String(encryptionContext.getContextDescriptor());
                        Log.d("evEncDesc", evEncDesc);

                        MEncryptedRequest encryptedRequest = new MEncryptedRequest(payload, evToken, evEncDesc);
                        Log.d("encryptedRequest", encryptedRequest.toString());

                        JSONObject encryptedRequestJson = new JSONObject();
                        encryptedRequestJson.put("payload", payload);
                        encryptedRequestJson.put("evToken", evToken);
                        encryptedRequestJson.put("evEncDesc", evEncDesc);
                        encryptedRequestJson.put("verificationTokenAsBase64", verificationTokenAsBase64); // Menambahkan token verifikasi di luar payload

                        callback.invoke(null, encryptedRequestJson.toString());

                    } catch (Exception e) {
                        callback.invoke("Error: " + e.getMessage(), null);
                    }
                });
            }
        }.setTimeout(10000).execute();
    }*/


  /*  @ReactMethod
    public void decrypt(String encryptedPayload, Callback callback) {
        EversafeHelper.getInstance().getEncryptionContext(200, new EncryptionContextAction() {
            @Override
            public void doAction(EncryptionContext encryptionContext) {
                try {
                    // Decode the encrypted payload from Base64
                    byte[] bytePayload = Base64.decodeBase64(encryptedPayload);
                    Log.d("bytePayload", String.valueOf(bytePayload));

                    // Decrypt the payload
                    byte[] byteDecrypt = encryptionContext.decrypt(bytePayload);
                    Log.d("byteDecrypt", String.valueOf(byteDecrypt));

                    // Convert decrypted byte array to String
                    String decryptPayload = new String(byteDecrypt, StandardCharsets.UTF_8);
                    Log.d("decryptPayload", String.valueOf(decryptPayload));

                    // Send decrypted payload back to React Native
                    callback.invoke(null, decryptPayload);
                } catch (Exception e) {
                    // Handle exception and send error message to React Native
                    callback.invoke("Error: " + e.getMessage(), null);
                }
            }
        });
    }*/

}



    /*@ReactMethod
    public MEncryptedRequest login(String username, String password, Callback callback) {
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

                *//*ApiConfig.getLoginInstance().postLogin(encryptedRequest).enqueue(new retrofit2.Callback<EverspinResponse>() {
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
                });*//*
            } catch (Exception e) {
                callback.invoke("Error: " + e.getMessage(), null);
            }
        });
    }*/
