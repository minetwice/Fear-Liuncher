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

        Button installButton =
                findViewById(R.id.installButton);

        installButton.setOnClickListener(v -> {

            startInstall();
        });
    }

    private void startInstall() {

        progressBar.setVisibility(View.VISIBLE);

        progressBar.setProgress(0);

        statusText.setText(
                "Preparing launcher..."
        );

        RuntimeInstaller.installAll(

                this,

                new RuntimeInstaller.InstallCallback() {

                    @Override
                    public void onProgress(String text) {

                        runOnUiThread(() -> {

                            statusText.setText(text);

                            int current =
                                    progressBar.getProgress();

                            if (current < 100) {

                                progressBar.setProgress(
                                        current + 5
                                );
                            }
                        });
                    }

                    @Override
                    public void onFinished() {

                        runOnUiThread(() -> {

                            progressBar.setProgress(100);

                            statusText.setText(
                                    "Launcher Ready"
                            );

                            new Handler().postDelayed(() -> {

                                startActivity(

                                        new Intent(
                                                SetupActivity.this,
                                                DashboardActivity.class
                                        )
                                );

                                finish();

                            }, 1500);
                        });
                    }

                    @Override
                    public void onError(Exception e) {

                        runOnUiThread(() -> {

                            statusText.setText(
                                    "Install Failed:\n"
                                            + e.getMessage()
                            );
                        });
                    }
                }
        );
    }
}
