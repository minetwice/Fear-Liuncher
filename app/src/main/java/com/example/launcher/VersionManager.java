package com.example.launcher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionManager {

    public interface VersionListener {

        void onVersionFound(
                String versionId,
                String versionUrl
        );

        void onError(Exception e);
    }

    public static void fetchLatestRelease(
            VersionListener listener
    ) {

        new Thread(() -> {

            try {

                URL url = new URL(
                        "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json"
                );

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        url.openStream()
                                )
                        );

                StringBuilder builder =
                        new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {

                    builder.append(line);
                }

                reader.close();

                JSONObject json =
                        new JSONObject(
                                builder.toString()
                        );

                JSONObject latest =
                        json.getJSONObject("latest");

                String latestRelease =
                        latest.getString("release");

                JSONArray versions =
                        json.getJSONArray("versions");

                for (int i = 0; i < versions.length(); i++) {

                    JSONObject version =
                            versions.getJSONObject(i);

                    if (version.getString("id")
                            .equals(latestRelease)) {

                        String versionUrl =
                                version.getString("url");

                        listener.onVersionFound(
                                latestRelease,
                                versionUrl
                        );

                        return;
                    }
                }

            } catch (Exception e) {

                listener.onError(e);
            }

        }).start();
    }
}
