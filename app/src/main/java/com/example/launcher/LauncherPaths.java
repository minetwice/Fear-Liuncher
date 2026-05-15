package com.example.launcher;

import android.content.Context;

import java.io.File;

public class LauncherPaths {

    public static File ROOT;

    public static File CACHE;
    public static File LOCAL;
    public static File MINECRAFT;

    public static File ASSETS;
    public static File LIBRARIES;
    public static File VERSIONS;
    public static File MODS;
    public static File SAVES;
    public static File RESOURCEPACKS;
    public static File SHADERPACKS;

    public static File LWJL3;
    public static File CACIOCAVALLO;
    public static File CACIOCAVALLO17;

    public static File CONTROLMAP;
    public static File INSTANCES;
    public static File SHARED_DIR;

    public static File LOG_FILE;

    public static void init(Context context) {

        ROOT = new File(
                context.getExternalFilesDir(null),
                "FearLauncher"
        );

        CACHE = new File(ROOT, ".cache");
        LOCAL = new File(ROOT, ".local");

        MINECRAFT = new File(ROOT, ".minecraft");

        ASSETS = new File(MINECRAFT, "assets");
        LIBRARIES = new File(MINECRAFT, "libraries");
        VERSIONS = new File(MINECRAFT, "versions");
        MODS = new File(MINECRAFT, "mods");
        SAVES = new File(MINECRAFT, "saves");
        RESOURCEPACKS = new File(MINECRAFT, "resourcepacks");
        SHADERPACKS = new File(MINECRAFT, "shaderpacks");

        LWJL3 = new File(ROOT, "lwjgl3");

        CACIOCAVALLO = new File(ROOT, "caciocavallo");
        CACIOCAVALLO17 = new File(ROOT, "caciocavallo17");

        CONTROLMAP = new File(ROOT, "controlmap");

        INSTANCES = new File(ROOT, "instances");

        SHARED_DIR = new File(ROOT, "shared_dir");

        LOG_FILE = new File(ROOT, "latestlog.txt");
    }

    public static void createAll() {

        ROOT.mkdirs();

        CACHE.mkdirs();
        LOCAL.mkdirs();

        MINECRAFT.mkdirs();

        ASSETS.mkdirs();
        LIBRARIES.mkdirs();
        VERSIONS.mkdirs();
        MODS.mkdirs();
        SAVES.mkdirs();
        RESOURCEPACKS.mkdirs();
        SHADERPACKS.mkdirs();

        LWJL3.mkdirs();

        CACIOCAVALLO.mkdirs();
        CACIOCAVALLO17.mkdirs();

        CONTROLMAP.mkdirs();

        INSTANCES.mkdirs();

        SHARED_DIR.mkdirs();

        try {

            if (!LOG_FILE.exists()) {
                LOG_FILE.createNewFile();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
