package com.example.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MirrorDownloader {

    public interface DownloadListener {

        void onProgress(String fileName, int progress);

        void onSuccess(File file);

        void onFailed();
    }

    public static void downloadFile(
            String fileName,
            String[] mirrors,
            File destination,
            DownloadListener listener
    ) {

        for (String mirror : mirrors) {

            try {

                URL url = new URL(mirror);

                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                connection.connect();

                int fileLength = connection.getContentLength();

                InputStream inputStream =
                        connection.getInputStream();

                FileOutputStream outputStream =
                        new FileOutputStream(destination);

                byte[] buffer = new byte[4096];

                long total = 0;

                int count;

                while ((count = inputStream.read(buffer)) != -1) {

                    total += count;

                    if (fileLength > 0) {

                        int progress =
                                (int) (total * 100 / fileLength);

                        listener.onProgress(fileName, progress);
                    }

                    outputStream.write(buffer, 0, count);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();

                listener.onSuccess(destination);

                return;

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        listener.onFailed();
    }
}
