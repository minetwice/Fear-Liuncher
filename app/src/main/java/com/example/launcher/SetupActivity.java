package com.example.launcher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SetupActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView statusText;
    private Button downloadButton;

    private static final int STORAGE_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        progressBar = findViewById(R.id.progressBar);
        statusText = findViewById(R.id.statusText);
        downloadButton = findViewById(R.id.downloadButton);

        progressBar.setMax(100);

        File gameDir = new File(getExternalFilesDir(null), "FearLauncher");

        if (!gameDir.exists()) {
            gameDir.mkdirs();
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },
                        STORAGE_PERMISSION_CODE
                );

            }

        }

        downloadButton.setOnClickListener(v -> {

            statusText.setText("Starting Download...");

            downloadFile(
                    "https://piston-data.mojang.com/v1/objects/fd1bfd99905340790c2f0c2d92b8bbc65ff9f28d/client.jar",
                    "minecraft-client.jar"
            );

        });

    }

    private void downloadFile(String urlString, String fileName) {

        new Thread(() -> {

            try {

                File folder = new File(getExternalFilesDir(null), "FearLauncher");

                if (!folder.exists()) {
                    folder.mkdirs();
                }

                File outputFile = new File(folder, fileName);

                URL url = new URL(urlString);

                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.connect();

                int fileLength = connection.getContentLength();

                InputStream inputStream = connection.getInputStream();

                FileOutputStream outputStream =
                        new FileOutputStream(outputFile);

                byte[] data = new byte[4096];

                long total = 0;

                int count;

                while ((count = inputStream.read(data)) != -1) {

                    total += count;

                    outputStream.write(data, 0, count);

                    if (fileLength > 0) {

                        int progress =
                                (int) (total * 100 / fileLength);

                        runOnUiThread(() -> {

                            progressBar.setProgress(progress);

                            statusText.setText(
                                    "Downloading: "
                                            + fileName
                                            + " ("
                                            + progress
                                            + "%)"
                            );

                        });

                    }

                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();

                runOnUiThread(() -> {

                    progressBar.setProgress(100);

                    statusText.setText(
                            "Download Complete:\n"
                                    + outputFile.getAbsolutePath()
                    );

                });

            } catch (Exception e) {

                runOnUiThread(() -> {

                    statusText.setText(
                            "Download Failed:\n"
                                    + e.getMessage()
                    );

                });

            }

        }).start();

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode == STORAGE_PERMISSION_CODE) {

            boolean granted = true;

            for (int result : grantResults) {

                if (result != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }

            }

            if (granted) {

                statusText.setText("Storage Permission Granted");

            } else {

                statusText.setText("Storage Permission Denied");

            }

        }

    }

}
