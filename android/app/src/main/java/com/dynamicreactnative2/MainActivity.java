package com.dynamicreactnative2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;
import com.secuchart.android.sdk.internal.FakeFinder;
import java.util.HashMap;

import kr.co.everspin.eversafe.EversafeHelper;

public class MainActivity extends ReactActivity {
  private static final String TAG = "FakeFinderResult";
  private int retryEverspin = 0;
  private final Handler handler = new Handler();

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "DynamicReactNative2";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. There the RootView is created and
   * you can specify the rendered you wish to use (Fabric or the older renderer).
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new MainActivityDelegate(this, getMainComponentName());
  }

  public static class MainActivityDelegate extends ReactActivityDelegate {
    public MainActivityDelegate(ReactActivity activity, String mainComponentName) {
      super(activity, mainComponentName);
    }

    @Override
    protected ReactRootView createRootView() {
      ReactRootView reactRootView = new ReactRootView(getContext());
      // If you opted-in for the New Architecture, we enable the Fabric Renderer.
      reactRootView.setIsFabric(BuildConfig.IS_NEW_ARCHITECTURE_ENABLED);
      return reactRootView;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTheme(R.style.AppTheme);

    EversafeService eversafeService = new EversafeService(this);
    HashMap<String, Object> additionalInfo = new HashMap<>();
//    additionalInfo.put("serverPublicKeyHashes", new String[]{"RmVKp+h3D32SChiehm0NoLAVScehQTfXHqKS+YsuKKI="});
//    EversafeHelper.getInstance().initialize("http://103.96.146.239:4443/eversafe", "870A5359781713B8", additionalInfo);
    // additionalInfo.put("serverPublicKeyHashes", new String[]{"bS0AvWWrr/DWgviFYXtEWljDIfzimiiRLDQZOUgapWM="});
    additionalInfo.put("serverPublicKeyHashes", new String[]{"yzqUW8DXncnTIPxVYPhFORxljC/zcafCfLZNp8MCdEg="});
    EversafeHelper.getInstance().initialize("https://eversafe.everspin.my.id/eversafe", "870A5359781713B8", additionalInfo);
    EversafeHelper.getInstance().setSubscriber(eversafeService);

    // FakeFinder fakeFinderInstance = FakeFinder.getInstance();
    // fakeFinderInstance.setLicenseKey("6a90ced7191ca4f61a8fe1e201e33c0327742964");
    // fakeFinderInstance.setSiteId("fakefinderid");
    // eversafeService.registerFakeFinderCallback();
    // eversafeService.registerRemoteAppCallback();
    // fakeFinderInstance.startFakeFinderWithContext(this, eversafeService);
    // fakeFinderInstance.fetchFakeAppResult();
    // handler.post(checkRunnable);
  }

  private final Runnable checkRunnable = new Runnable() {
    @Override
    public void run() {
      if (EversafeService.isFakeFinderStatus && !EversafeService.isEversafeStatus || EversafeService.remoteAppStatus) {
        Log.d(TAG, "run +" + EversafeService.fakeApps);
        intentFakeFinderActivity();
      } else if (retryEverspin < 3) {
        Log.d(TAG, "retry");
        retryEverspin++;
        handler.removeCallbacks(this);
        handler.postDelayed(this, 2000);
      }
    }
  };

  private void intentFakeFinderActivity() {
    Intent intent = new Intent(this, FakeFinderActivity.class);
    startActivity(intent);
  }
}
