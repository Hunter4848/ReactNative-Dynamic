package com.dynamicreactnative2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.dynamicreactnative2.FakeFinderResultAdapter;
import com.secuchart.android.sdk.base.model.fake_app.FakeAppResult;

public class FakeFinderActivity extends AppCompatActivity implements FakeFinderResultAdapter.OnDeleteItemClickListener {

    private static final String TAG = "FakeFinderResult";
    private FakeFinderResultAdapter mAdapter;
    private RecyclerView fakefinderListItem;
    private View imageCaution;
    private View titleCaution;
    private View messageCaution;
    private RemoteAppResultAdapter mAdapterRemote;
    private RecyclerView remoteListItem;
    private int mDeletePosition = -1;
    private String packageName = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_finder);

        LinearLayout fakefinderLinearLayout = findViewById(R.id.fakefinder_layout);
        fakefinderListItem = findViewById(R.id.FakeFinder_ListItem);
        Button buttonCloseFakeFinder = findViewById(R.id.Button_Close_ListFakeFinder);

        imageCaution = findViewById(R.id.Eversafe_Image_Caution);
        titleCaution = findViewById(R.id.Eversafe_Title_Caution);
        messageCaution = findViewById(R.id.Eversafe_Message_Caution);

        LinearLayout remoteRemoteLinearLayout = findViewById(R.id.remote_layout);
        remoteListItem = findViewById(R.id.RemoteApp_ListItem);

        buttonCloseFakeFinder.setOnClickListener(v -> {
            List<FakeAppResult> fakeAppsList = mAdapter.list;
            for (FakeAppResult app : fakeAppsList) {
                if ("DANGER".equals(app.getResult().toString())) {
                    Toast.makeText(getApplicationContext(), "Please delete danger app!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            finishAffinity();
            System.exit(0);
        });

        if (!EversafeService.fakeApps.isEmpty()) {
            mAdapter = new FakeFinderResultAdapter(this, EversafeService.fakeApps);
            mAdapter.setOnDeleteItemClickListener(this);
            fakefinderListItem.setAdapter(mAdapter);
            fakefinderListItem.setLayoutManager(new LinearLayoutManager(this));
            mAdapter.notifyDataSetChanged();
        } else {
            fakefinderLinearLayout.setVisibility(View.GONE);
        }

        if (!EversafeService.remoteApps.isEmpty()) {
            mAdapterRemote = new RemoteAppResultAdapter(this, EversafeService.remoteApps);
            remoteListItem.setAdapter(mAdapterRemote);
            remoteListItem.setLayoutManager(new LinearLayoutManager(this));
            mAdapterRemote.notifyDataSetChanged();
        } else {
            remoteRemoteLinearLayout.setVisibility(View.GONE);
        }
    }

    private boolean hasDangerApps() {
        if (mAdapter != null) {
            for (FakeAppResult app : mAdapter.list) {
                if ("DANGER".equals(app.getResult().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onDeleteItemClick(int position) {
        packageName = EversafeService.fakeApps.get(position).getPackageId();
        Uri packageUri = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
        startActivityForResult(uninstallIntent, 101);
        mDeletePosition = position;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            // Periksa apakah aplikasi berhasil dihapus setelah startActivity
            PackageManager pm = getPackageManager();
            try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                // Package masih ada, berarti belum dihapus
            } catch (PackageManager.NameNotFoundException e) {
                // Package tidak ditemukan, berarti sudah dihapus
                mAdapter.removeItem(mDeletePosition);
                mAdapter.notifyDataSetChanged();

                if (!hasDangerApps()) {
                    titleCaution.setVisibility(View.GONE);
                    messageCaution.setVisibility(View.GONE);

                    // Jika tidak ada aplikasi FakeApp dan RemoteApp, tutup aplikasi
                    int hasRemoteApps = mAdapterRemote != null ? mAdapterRemote.getItemCount() : 0;
                    if ((mAdapter == null || mAdapter.getItemCount() == 0) && hasRemoteApps <= 0) {
                        finishAffinity();
                        System.exit(0);
                    }
                }
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        return super.dispatchKeyEvent(event);
    }
}