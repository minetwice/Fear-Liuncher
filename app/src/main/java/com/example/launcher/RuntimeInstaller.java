package com.example.launcher;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class RuntimeInstaller {

    public interface InstallCallback {
        void onProgress(String text);
        void onFinished();
        void onError(Exception e);
    }

    public static void installAll(
            Context context,
            InstallCallback callback
    ) {

        LauncherPaths.init(context);

        LauncherPaths.createAll();

        callback.onProgress("Downloading Java 17 Runtime...");

        File java17 =
                new File(
                        LauncherPaths.ROOT,
                        "runtime/java17.zip"
                );

        FileDownloader.download(

                "https://github.com/PojavLauncherTeam/android-openjdk-build-multiarch/releases/download/jre17/jre17-pojav.tar.xz",

                java17,

                new FileDownloader.DownloadCallback() {

                    @Override
                    public void onProgress(int progress) {

                        callback.onProgress(
                                "Java17 Download: " + progress + "%"
                        );
                    }

                    @Override
                    public void onFinish(File file) {

                        Log.d(
                                "FearLauncher",
                                "Java17 Downloaded"
                        );

                        callback.onProgress(
                                "Java17 Installed"
                        );

                        callback.onFinished();
                    }

                    @Override
                    public void onError(Exception e) {

                        callback.onError(e);
                    }
                }
        );
    }
}
