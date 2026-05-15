package com.example.launcher;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class SetupActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private TextView statusText;

    private final Handler handler =
            new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setGravity(Gravity.CENTER);

        layout.setBackgroundColor(Color.BLACK);

        TextView title = new TextView(this);

        title.setText("Fear Launcher");

        title.setTextColor(Color.WHITE);

        title.setTextSize(28);

        statusText = new TextView(this);

        statusText.setTextColor(Color.LTGRAY);

        progressBar =
                new ProgressBar(
                        this,
                        null,
                        android.R.attr.progressBarStyleHorizontal
                );

        progressBar.setMax(100);

        layout.addView(title);

        layout.addView(progressBar);

        layout.addView(statusText);

        setContentView(layout);

        startInstaller();
    }

    private void startInstaller() {

        new Thread(() -> {

            RuntimeInstaller installer =
                    new RuntimeInstaller(this);

            installer.install(
                    new MirrorDownloader.DownloadListener() {

                        @Override
                        public void onProgress(
                                String fileName,
                                int progress
                        ) {

                            handler.post(() -> {

                                statusText.setText(
                                        "Downloading: "
                                                + fileName
                                );

                                progressBar.setProgress(progress);
                            });
                        }

                        @Override
                        public void onSuccess(File file) {

                            handler.post(() -> {

                                statusText.setText(
                                        "Installed: "
                                                + file.getName()
                                );
                            });
                        }

                        @Override
                        public void onFailed() {

                            handler.post(() -> {

                                statusText.setText(
                                        "Installation Failed"
                                );
                            });
                        }
                    }
            );

        }).start();
    }
}
