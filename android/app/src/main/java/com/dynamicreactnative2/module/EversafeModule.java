package com.dynamicreactnative2.module;

import android.util.Log;

import com.dynamicreactnative2.model.EversafeRequest;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import kr.co.everspin.eversafe.EncryptionContext;
import kr.co.everspin.eversafe.EversafeHelper;
import kr.co.everspin.eversafe.components.base64.Base64;
import java.sql.Timestamp;


public class EversafeModule extends ReactContextBaseJavaModule {

    private static ReactApplicationContext reactContext;

    private EncryptionContext encContext;

    public EversafeModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
    }

    @Override
    public String getName() {
        return "EversafeModule";
    }

    @ReactMethod
    public void encrypt(String jsonString, Callback callback) {
    new EversafeHelper.GetVerificationTokenTask() {
        @Override
        public void onAction(byte[] bytes, String verificationTokenAsBase64, int result) {
            Log.d("verification", "verificationTokenAsBase64: " + verificationTokenAsBase64);

            EversafeHelper.getInstance().getEncryptionContext(200, encryptionContext -> {
                encContext = encryptionContext;
                try {
                    byte[] byteArray = jsonString.getBytes(StandardCharsets.UTF_8);
                    Log.d("byteArray", Arrays.toString(byteArray));

                    String payload = Base64.encodeBase64String(encryptionContext.encrypt(byteArray));
                    Log.d("payload", payload);

                    String evToken = kr.co.everspin.eversafe.components.base64.Base64.encodeBase64String(
                            encryptionContext.encrypt(encryptionContext.getVerificationToken())
                    );
                    Log.d("evToken", evToken);

                    String evEncDesc = kr.co.everspin.eversafe.components.base64.Base64.encodeBase64String(
                            encryptionContext.getContextDescriptor()
                    );
                    Log.d("evEncDesc", evEncDesc);

//                   long timestamp = System.currentTimeMillis();
//                   Log.d("timestamp", "Timestamp: " + timestamp);

                    // Generate timestamp in seconds
                   Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                   long timestampInSeconds = timestamp.getTime() / 1000;
                   Log.d("timestamp", "Timestamp (seconds): " + timestampInSeconds);

                    EversafeRequest encryptedRequest = new EversafeRequest(payload, evToken, evEncDesc);
                    Log.d("encryptedRequest", encryptedRequest.toString());

                    JSONObject encryptedRequestJson = new JSONObject();
                    encryptedRequestJson.put("payload", payload);
                    encryptedRequestJson.put("evToken", evToken);
                    encryptedRequestJson.put("evEncDesc", evEncDesc);

                    // Add timestamp in seconds
//                    encryptedRequestJson.put("timestamp", timestampInSeconds);

                    callback.invoke(null, encryptedRequestJson.toString());
                } catch (Exception e) {
                    callback.invoke("Error: " + e.getMessage(), null);
                }
            });
        }
    }.setTimeout(10000).execute();
}

    @ReactMethod
    public void decrypt(String encryptedPayload, Callback callback) {

        byte[] bytePayload = Base64.decodeBase64(encryptedPayload);
        Log.d("bytePayload", Arrays.toString(bytePayload));

        byte[] byteDecrypt = encContext.decrypt(bytePayload);
        Log.d("byteDecrypt", Arrays.toString(byteDecrypt));

        String decryptPayload = new String(byteDecrypt, StandardCharsets.UTF_8);
        Log.d("decryptPayload", decryptPayload);

        callback.invoke(null, decryptPayload);
    }
}
