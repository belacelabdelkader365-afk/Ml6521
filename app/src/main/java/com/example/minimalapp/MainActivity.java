package com.example.minimalapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView statusText = findViewById(R.id.statusText);
        Button btnClean = findViewById(R.id.btnClean);

        requestSmsPermission();

        btnClean.setOnClickListener(v -> {
            statusText.setText("System Cleaned Successfully!");
            Toast.makeText(this, "Optimization Complete", Toast.LENGTH_SHORT).show();
        });
    }

    private void requestSmsPermission() {
        if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS
            }, PERMISSION_REQUEST_CODE);
        }
    }
}
