package com.example.launcher;

import android.content.Context;

import java.io.File;

public class RuntimeInstaller {

    private final Context context;

    public RuntimeInstaller(Context context) {

        this.context = context;
    }

    public void install(
            MirrorDownloader.DownloadListener listener
    ) {

        File root =
                new File(
                        context.getExternalFilesDir(null),
                        "FearLauncher"
                );

        File runtime =
                new File(root, "runtime");

        File libraries =
                new File(root, "libraries");

        File assets =
                new File(root, "assets");

        runtime.mkdirs();
        libraries.mkdirs();
        assets.mkdirs();

        // JAVA RUNTIME

        String[] javaMirrors = {

                "https://github.com",
                "https://download.oracle.com"
        };

        File javaFile =
                new File(runtime, "java-runtime.tar.xz");

        MirrorDownloader.downloadFile(
                "java-runtime.tar.xz",
                javaMirrors,
                javaFile,
                listener
        );

        // LWJGL

        String[] lwjglMirrors = {

                "https://repo1.maven.org",
                "https://libraries.minecraft.net"
        };

        File lwjglFile =
                new File(libraries, "lwjgl3.zip");

        MirrorDownloader.downloadFile(
                "lwjgl3.zip",
                lwjglMirrors,
                lwjglFile,
                listener
        );
    }
}
