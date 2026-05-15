package com.example.launcher;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SetupActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView statusText;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        progressBar = findViewById(R.id.progressBar);
        statusText = findViewById(R.id.statusText);

        startLauncherPreparation();
    }

    private void startLauncherPreparation() {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {

            fakeStep("Checking launcher files...", 10);
            fakeStep("Loading runtime...", 25);
            fakeStep("Preparing assets...", 45);
            fakeStep("Optimizing launcher...", 65);
            fakeStep("Finalizing setup...", 85);
            fakeStep("Launcher Ready!", 100);

        });

    }

    private void fakeStep(String text, int progress) {

        try {
            Thread.sleep(700);
        } catch (InterruptedException ignored) {
        }

        handler.post(() -> {
            statusText.setText(text);
            progressBar.setProgress(progress);
        });

    }
}
