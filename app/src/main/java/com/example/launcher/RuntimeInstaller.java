package com.example.launcher;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;

public class RuntimeInstaller {

    public static void installAll() {

        writeLog("FearLauncher Installer Started");

        installJava("java17");
        installJava("java21");
        installJava("java25");

        installLWJGL();

        installCacio();

        writeLog("Installation Completed");
    }

    private static void installJava(String version) {

        File javaDir = new File(
                LauncherPaths.ROOT,
                "runtime/" + version
        );

        javaDir.mkdirs();

        writeLog("Installed " + version);
    }

    private static void installLWJGL() {

        LauncherPaths.LWJL3.mkdirs();

        writeLog("LWJGL Installed");
    }

    private static void installCacio() {

        LauncherPaths.CACIOCAVALLO.mkdirs();
        LauncherPaths.CACIOCAVALLO17.mkdirs();

        writeLog("Caciocavallo Installed");
    }

    private static void writeLog(String text) {

        try {

            FileWriter writer =
                    new FileWriter(
                            LauncherPaths.LOG_FILE,
                            true
                    );

            writer.append(text).append("\n");

            writer.close();

            Log.d("FearLauncher", text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
