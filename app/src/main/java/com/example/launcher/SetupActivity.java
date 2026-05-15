package com.example.launcher;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SetupActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView statusText;
    Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        progressBar = findViewById(R.id.progressBar);
        statusText = findViewById(R.id.statusText);
        downloadButton = findViewById(R.id.downloadButton);

        progressBar.setMax(100);

        createLauncherFolder();

        downloadButton.setOnClickListener(v -> {

            statusText.setText("Starting Download...");

            downloadFile(
                    "https://launchermeta.mojang.com/mc/game/version_manifest.json",
                    "version_manifest.json"
            );

        });

    }

    private void createLauncherFolder() {

        File folder = new File(
                getExternalFilesDir(null),
                "FearLauncher"
        );

        if (!folder.exists()) {

            boolean created = folder.mkdirs();

            if (created) {

                Toast.makeText(
                        this,
                        "Folder Created",
                        Toast.LENGTH_SHORT
                ).show();

            }

        }

    }

    private void downloadFile(String urlString, String fileName) {

        new Thread(() -> {

            HttpURLConnection connection = null;

            try {

                File folder = new File(
                        getExternalFilesDir(null),
                        "FearLauncher"
                );

                if (!folder.exists()) {
                    folder.mkdirs();
                }

                File outputFile = new File(folder, fileName);

                URL url = new URL(urlString);

                connection =
                        (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setDoInput(true);

                connection.connect();

                int responseCode =
                        connection.getResponseCode();

                if (responseCode != HttpURLConnection.HTTP_OK) {

                    runOnUiThread(() -> {

                        statusText.setText(
                                "Server Error: "
                                        + responseCode
                        );

                    });

                    return;
                }

                int fileLength =
                        connection.getContentLength();

                InputStream inputStream =
                        connection.getInputStream();

                FileOutputStream outputStream =
                        new FileOutputStream(outputFile);

                byte[] buffer = new byte[4096];

                long total = 0;

                int count;

                while ((count =
                        inputStream.read(buffer)) != -1) {

                    total += count;

                    outputStream.write(
                            buffer,
                            0,
                            count
                    );

                    if (fileLength > 0) {

                        int progress =
                                (int) (total * 100 / fileLength);

                        runOnUiThread(() -> {

                            progressBar.setProgress(progress);

                            statusText.setText(
                                    "Downloading:\n"
                                            + fileName
                                            + "\n"
                                            + progress
                                            + "%"
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
                            "Download Complete\n\nSaved To:\n"
                                    + outputFile.getAbsolutePath()
                    );

                    Toast.makeText(
                            SetupActivity.this,
                            "File Downloaded",
                            Toast.LENGTH_LONG
                    ).show();

                });

            } catch (Exception e) {

                runOnUiThread(() -> {

                    statusText.setText(
                            "Download Failed\n\n"
                                    + e.toString()
                    );

                });

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }

            }

        }).start();

    }

}
