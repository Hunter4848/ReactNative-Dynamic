package com.dynamicreactnative2;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.secuchart.android.sdk.base.model.remote_app.RemoteAppResult;

import java.util.List;

import com.dynamicreactnative2.R;

public class RemoteAppResultAdapter extends RecyclerView.Adapter<RemoteAppResultAdapter.RemoteAppViewHolder> {

    private final Context context;
    private List<RemoteAppResult> list;

    public RemoteAppResultAdapter(Context context, List<RemoteAppResult> list) {
        this.context = context;
        this.list = list;
    }

    public List<RemoteAppResult> getListRemoteApps() {
        return list;
    }

    public static class RemoteAppViewHolder extends RecyclerView.ViewHolder {
        ImageView remoteImage;
        TextView remoteAppTitle;
        TextView remoteAppPackage;

        public RemoteAppViewHolder(View itemView) {
            super(itemView);
            remoteImage = itemView.findViewById(R.id.RemoteApp_Image);
            remoteAppTitle = itemView.findViewById(R.id.RemoteApp_Title);
            remoteAppPackage = itemView.findViewById(R.id.RemoteApp_Package);
        }
    }

    @NonNull
    @Override
    public RemoteAppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.eversafe_item_threat_detection_list_remoteapp, parent, false);
        return new RemoteAppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemoteAppViewHolder holder, int position) {
        RemoteAppResult item = list.get(position);
        bindRemoteApp(holder, item);
    }

    private void bindRemoteApp(RemoteAppViewHolder holder, RemoteAppResult item) {
        try {
            // Menampilkan ikon aplikasi berdasarkan package name
            Drawable appIcon = context.getPackageManager().getApplicationIcon(item.getPackageId());
            holder.remoteImage.setImageDrawable(appIcon);

            // Menampilkan nama aplikasi berdasarkan package name
            CharSequence appName = context.getPackageManager().getApplicationLabel(
                    context.getPackageManager().getApplicationInfo(item.getPackageId(), 0)
            );
            holder.remoteAppTitle.setText(appName);

            // Menampilkan nama package aplikasi (misalnya, com.teamviewer.host.market)
            holder.remoteAppPackage.setText(item.getPackageId());

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<RemoteAppResult> mList) {
        list.clear();
        list.addAll(mList);
        notifyDataSetChanged();
    }
}
