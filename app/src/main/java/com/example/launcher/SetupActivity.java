package com.example.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup);

        progressBar = findViewById(R.id.progressBar);
        statusText = findViewById(R.id.statusText);

        Button installButton = findViewById(R.id.installButton);

        installButton.setOnClickListener(v -> startInstall());
    }

    private void startInstall() {

        progressBar.setVisibility(View.VISIBLE);

        statusText.setText("Creating launcher directories...");

        LauncherPaths.init(this);

        LauncherPaths.createAll();

        new Handler().postDelayed(() -> {

            statusText.setText("Installing runtimes...");

            RuntimeInstaller.installAll();

        }, 1500);

        new Handler().postDelayed(() -> {

            statusText.setText("Preparing Minecraft environment...");

        }, 3000);

        new Handler().postDelayed(() -> {

            statusText.setText("Installation Complete");

            startActivity(
                    new Intent(this, DashboardActivity.class)
            );

            finish();

        }, 5000);
    }
}
