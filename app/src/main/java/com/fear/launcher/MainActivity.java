package com.fear.launcher;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button launchBtn = findViewById(R.id.launchButton);
        launchBtn.setOnClickListener(v -> {
            // Temporary: Show that launcher is working
            Toast.makeText(this, "Fear Launcher - Ready!", Toast.LENGTH_SHORT).show();
            
            // TODO: Add actual Minecraft Java Edition launch logic
            // 1. Check if Java runtime exists
            // 2. Run java -jar minecraft_server.jar or use internal JVM
            // 3. Handle OpenGL rendering via Pojav's JNI bridge
        });
    }
}
