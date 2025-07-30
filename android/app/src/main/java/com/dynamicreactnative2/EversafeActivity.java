package com.dynamicreactnative2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EversafeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eversafe_layout_threat_detection);

        LinearLayout eversafeLinearLayout = findViewById(R.id.eversafe_Linear_layout);
        TextView evsMessageTitleTxt = findViewById(R.id.Eversafe_Title);
        TextView evsMessageTxt = findViewById(R.id.Eversafe_Message);
        Button evsButtonCloseEversafe = findViewById(R.id.Button_CloseEversafe);

        Intent intent = getIntent();
        String title = intent.getStringExtra("evsTitle");
        String message = intent.getStringExtra("evsMessage");

        evsMessageTitleTxt.setText(title);
        evsMessageTxt.setText(message);

        evsButtonCloseEversafe.setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        return super.dispatchKeyEvent(event);
    }
}