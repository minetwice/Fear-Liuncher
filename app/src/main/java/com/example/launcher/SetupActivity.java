package com.example.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

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

        installButton.setOnClickListener(v -> installLauncher());
    }

    private void installLauncher() {

        progressBar.setVisibility(View.VISIBLE);

        statusText.setText("Creating launcher folders...");

        File root = new File(Environment.getExternalStorageDirectory(), "TidalLauncher");

        File java17 = new File(root, "java17");
        File java21 = new File(root, "java21");
        File java25 = new File(root, "java25");
        File runtime = new File(root, "runtime");
        File assets = new File(root, "assets");
        File game = new File(root, "game");

        root.mkdirs();
        java17.mkdirs();
        java21.mkdirs();
        java25.mkdirs();
        runtime.mkdirs();
        assets.mkdirs();
        game.mkdirs();

        new Handler().postDelayed(() -> {

            statusText.setText("Downloading Java 17...");

        }, 1000);

        new Handler().postDelayed(() -> {

            statusText.setText("Downloading Java 21...");

        }, 2500);

        new Handler().postDelayed(() -> {

            statusText.setText("Downloading Java 25...");

        }, 4000);

        new Handler().postDelayed(() -> {

            statusText.setText("Installation Complete");

            startActivity(new Intent(this, DashboardActivity.class));
            finish();

        }, 6000);
    }
}
