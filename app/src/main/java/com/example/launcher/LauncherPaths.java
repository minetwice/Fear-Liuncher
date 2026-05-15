package com.example.launcher;

import android.content.Context;

import java.io.File;

public class LauncherPaths {

    public static File ROOT;

    public static File JAVA_17;
    public static File JAVA_21;
    public static File JAVA_25;

    public static File JNI;
    public static File LOGS;
    public static File CACHE;
    public static File CONFIG;
    public static File DOWNLOADS;
    public static File RUNTIME;
    public static File LWJGL;

    public static File MC_ROOT;
    public static File MC_ASSETS;
    public static File MC_LIBRARIES;
    public static File MC_VERSIONS;
    public static File MC_MODS;
    public static File MC_SAVES;
    public static File MC_SHADERS;
    public static File MC_RESOURCEPACKS;

    public static void init(Context context) {

        ROOT = new File(
                context.getExternalFilesDir(null),
                "FearLauncher"
        );

        JAVA_17 = new File(ROOT, "java/java17");
        JAVA_21 = new File(ROOT, "java/java21");
        JAVA_25 = new File(ROOT, "java/java25");

        JNI = new File(ROOT, "jni");
        LOGS = new File(ROOT, "logs");
        CACHE = new File(ROOT, "cache");
        CONFIG = new File(ROOT, "config");
        DOWNLOADS = new File(ROOT, "downloads");
        RUNTIME = new File(ROOT, "runtime");
        LWJGL = new File(ROOT, "lwjgl");

        MC_ROOT = new File(ROOT, "minecraft");

        MC_ASSETS = new File(MC_ROOT, "assets");
        MC_LIBRARIES = new File(MC_ROOT, "libraries");
        MC_VERSIONS = new File(MC_ROOT, "versions");
        MC_MODS = new File(MC_ROOT, "mods");
        MC_SAVES = new File(MC_ROOT, "saves");
        MC_SHADERS = new File(MC_ROOT, "shaderpacks");
        MC_RESOURCEPACKS = new File(MC_ROOT, "resourcepacks");
    }

    public static void createAll() {

        ROOT.mkdirs();

        JAVA_17.mkdirs();
        JAVA_21.mkdirs();
        JAVA_25.mkdirs();

        JNI.mkdirs();
        LOGS.mkdirs();
        CACHE.mkdirs();
        CONFIG.mkdirs();
        DOWNLOADS.mkdirs();
        RUNTIME.mkdirs();
        LWJGL.mkdirs();

        MC_ROOT.mkdirs();

        MC_ASSETS.mkdirs();
        MC_LIBRARIES.mkdirs();
        MC_VERSIONS.mkdirs();
        MC_MODS.mkdirs();
        MC_SAVES.mkdirs();
        MC_SHADERS.mkdirs();
        MC_RESOURCEPACKS.mkdirs();
    }
}
