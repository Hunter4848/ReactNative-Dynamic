package com.dynamicreactnative2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.secuchart.android.sdk.base.FakeFinderReadyCallback;
import com.secuchart.android.sdk.base.listener.FakeAppDetectListener;
import com.secuchart.android.sdk.base.listener.RemoteAppDetectListener;
import com.secuchart.android.sdk.base.model.fake_app.FakeAppResult;
import com.secuchart.android.sdk.base.model.remote_app.RemoteAppResult;
import com.secuchart.android.sdk.internal.FakeFinder;
import java.util.ArrayList;
import java.util.List;
import kr.co.everspin.eversafe.EversafeThreat;
import kr.co.everspin.eversafe.subscriber.AbstractEversafeSubscriber;

public class EversafeService extends AbstractEversafeSubscriber implements FakeFinderReadyCallback {

    private static final String TAG = "fakeFinderServices";
    private final Context context;

    public static List<FakeAppResult> fakeApps = new ArrayList<>();
    public static List<RemoteAppResult> remoteApps = new ArrayList<>();
    public static boolean remoteAppStatus = false;
    public static boolean isFakeFinderStatus = false;
    public static boolean isEversafeStatus = false;

    public EversafeService(Context context) {
        this.context = context;
    }

    @Override
    public void onFakeFinderReady() {
        FakeFinder.getInstance().fetchFakeAppResult();
        FakeFinder.getInstance().fetchRemoteAppResult();
    }

    public void registerRemoteAppCallback() {
        FakeFinder.getInstance().registerRemoteAppDetectListener(new RemoteAppDetectListener() {
            @Override
            public void onResultSuccess(@Nullable String requestId, @Nullable List<? extends RemoteAppResult> remoteAppResults) {
                Log.d("remoteApps", "Request ID: " + requestId + ", Result: " + remoteAppResults);
                if (remoteAppResults != null && remoteAppResults.size() > 0) {
                    for (RemoteAppResult remoteApp : remoteAppResults) {
                        Log.d("remoteApps", "packageId : " + remoteApp.getPackageId());
                    }
                    remoteApps.clear();
                    remoteApps.addAll(remoteAppResults);
                    remoteAppStatus = true;
                    isFakeFinderStatus = false;
                }
            }

            @Override
            public void onResultFail(@Nullable String s, int i) {

            }
        });
    }

    public void registerFakeFinderCallback() {
        Log.d("called", "register called");
        FakeFinder.getInstance().registerFakeAppDetectListener(new FakeAppDetectListener() {
            @Override
            public void onResultSuccess(@Nullable String s, @Nullable List<? extends FakeAppResult> fakeAppResults) {
                if (fakeAppResults != null && fakeAppResults.size() > 0) {
                    Log.d(TAG, "register success");
                    fakeApps.clear();
                    fakeApps.addAll(fakeAppResults);
                    isFakeFinderStatus = true;
                    Log.d(TAG, fakeAppResults.toString());
                }
            }

            @Override
            public void onResultFail(@Nullable String s, int i) {

            }
        });
    }

    @Override
    public void onFakeFinderReadyFail(int errorCode) {
        // Handle failure if needed
    }

    @Override
    public void onEversafeError(String title, String message) {
        isEversafeStatus = true;
        openNewActivity(title, message);
    }

    @Override
    public void onEversafeThreatFound(ArrayList<EversafeThreat> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            isEversafeStatus = true;
            openNewActivity(arrayList.get(0).getCode(), arrayList.get(0).getLocalizedDescription());
        }
    }

    private void openNewActivity(String title, String message) {
        Intent intent = new Intent(context, EversafeActivity.class);
        intent.putExtra("evsTitle", title + " Detected");
        intent.putExtra("evsMessage", message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
