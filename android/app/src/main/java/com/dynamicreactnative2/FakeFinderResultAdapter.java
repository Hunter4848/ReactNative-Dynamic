package com.dynamicreactnative2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.secuchart.android.sdk.base.model.fake_app.FakeAppResult;
import java.util.ArrayList;
import java.util.List;
import com.dynamicreactnative2.R;

public class FakeFinderResultAdapter extends RecyclerView.Adapter<FakeFinderResultAdapter.myViewHolder> {
    private static final List<FakeAppResult> FakeAppResults = new ArrayList<>();
    private final Context context;
    public final List<FakeAppResult> list;
    private OnDeleteItemClickListener onDeleteItemClickListener;


    public FakeFinderResultAdapter(Context context, List<FakeAppResult> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.eversafe_item_threat_detection_fakefinder, viewGroup, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        FakeAppResult item = list.get(position);
        try {
            myViewHolder.evsImage.setImageDrawable(context.getPackageManager().getApplicationIcon(list.get(position).getPackageId()));
            myViewHolder.evsTitle.setText(context.getPackageManager().getApplicationLabel(
                    context.getPackageManager().getApplicationInfo(list.get(position).getPackageId(), 0)).toString());

            if(item.getResult().toString().equals("VALID")){
                myViewHolder.evsStatus.setText("VALID");
                myViewHolder.evsStatus.setTextColor(context.getResources().getColor(R.color.fakefinder_valid));
            } else if (item.getResult().toString().equals("INVALID")) {
                myViewHolder.evsStatus.setText("INVALID");
                myViewHolder.evsStatus.setTextColor(context.getResources().getColor(R.color.fakefinder_invalid));
            } else if (item.getResult().toString().equals("NOT_FOUND")){
                myViewHolder.evsStatus.setText("NOT_FOUND");
                myViewHolder.evsStatus.setTextColor(context.getResources().getColor(R.color.fakefinder_not_found));
            }else if (item.getResult().toString().equals("DANGER")){
                myViewHolder.evsStatus.setText("DANGER");
                myViewHolder.evsStatus.setTextColor(context.getResources().getColor(R.color.fakefinder_danger));
            }else {
                myViewHolder.evsStatus.setText("WARNING");
                myViewHolder.evsStatus.setTextColor(context.getResources().getColor(R.color.fakefinder_warning));
            }


            if(item.getReasonCode().equals("10001")){
                myViewHolder.evsReason.setText("Call hijacking (redirection)");
            } else if (item.getReasonCode().equals("10002")) {
                myViewHolder.evsReason.setText("Requiring excessive personal information");
            } else if (item.getReasonCode().equals("10003")){
                myViewHolder.evsReason.setText("Fake app");
            } else {
                myViewHolder.evsReason.setText("");
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        myViewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteItemClickListener.onDeleteItemClick(position);
            }
        });

    }

//    public void addItems(List<FakeAppResult> mList) {
//        this.list.clear();
//        this.list.addAll(mList);
//        notifyDataSetChanged();
//    }

    public void clearItems() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < list.size()) {
            list.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView evsImage;
        TextView evsTitle;
        TextView evsStatus;
        TextView evsReason;
        Button buttonDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            evsImage = itemView.findViewById(R.id.Eversafe_Image);
            evsTitle = itemView.findViewById(R.id.Eversafe_Title);
            evsStatus = itemView.findViewById(R.id.Eversafe_StatusFake);
            evsReason = itemView.findViewById(R.id.Eversafe_Reason);
            buttonDelete = itemView.findViewById(R.id.Button_Delete);
        }
    }


    public interface OnDeleteItemClickListener {
        void onDeleteItemClick(int position);
    }
    public void setOnDeleteItemClickListener(OnDeleteItemClickListener listener) {
        onDeleteItemClickListener = listener;
    }
}
