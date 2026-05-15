package com.example.launcher;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(() -> {

            try {
                Thread.sleep(500);
            } catch (Exception ignored) {}

            runOnUiThread(() -> {
                startActivity(new Intent(MainActivity.this, SetupActivity.class));
                finish();
            });

        }).start();
    }
}
