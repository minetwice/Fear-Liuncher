package com.example.launcher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.Gravity;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class SetupActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;

    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            // ROOT LAYOUT
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.CENTER);
            layout.setBackgroundColor(Color.BLACK);

            // TITLE
            TextView title = new TextView(this);
            title.setText("Fear Launcher");
            title.setTextColor(Color.WHITE);
            title.setTextSize(28);

            // STATUS TEXT
            statusText = new TextView(this);
            statusText.setText("Initializing...");
            statusText.setTextColor(Color.LTGRAY);
            statusText.setTextSize(16);

            // PROGRESS BAR
            ProgressBar progressBar = new ProgressBar(this);
            progressBar.setIndeterminate(true);

            layout.addView(title);
            layout.addView(progressBar);
            layout.addView(statusText);

            setContentView(layout);

            Log.d("FEAR", "Launcher Started");

            // ANDROID 11+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                createLauncherFolders();

            } else {

                checkStoragePermission();

            }

        } catch (Exception e) {

            Log.e("FEAR", "CRASH", e);

        }
    }

    private void checkStoragePermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    STORAGE_PERMISSION_CODE
            );

        } else {

            createLauncherFolders();

        }
    }

    private void createLauncherFolders() {

        try {

            File root;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                root = new File(
                        getExternalFilesDir(null),
                        "FearLauncher"
                );

            } else {

                root = new File(
                        Environment.getExternalStorageDirectory(),
                        "FearLauncher"
                );

            }

            // MAIN FOLDERS
            File runtime = new File(root, "runtime");
            File javaFolder = new File(runtime, "java");
            File lwjgl = new File(runtime, "lwjgl");
            File libraries = new File(root, "libraries");
            File assets = new File(root, "assets");
            File versions = new File(root, "versions");
            File natives = new File(root, "natives");
            File config = new File(root, "config");
            File logs = new File(root, "logs");

            runtime.mkdirs();
            javaFolder.mkdirs();
            lwjgl.mkdirs();
            libraries.mkdirs();
            assets.mkdirs();
            versions.mkdirs();
            natives.mkdirs();
            config.mkdirs();
            logs.mkdirs();

            statusText.setText("Folders Created Successfully");

            Log.d("FEAR", "All launcher folders created");

        } catch (Exception e) {

            statusText.setText("Folder Creation Failed");

            Log.e("FEAR", "FOLDER ERROR", e);

        }
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

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                createLauncherFolders();

            } else {

                statusText.setText("Storage Permission Denied");

            }
        }
    }
}
