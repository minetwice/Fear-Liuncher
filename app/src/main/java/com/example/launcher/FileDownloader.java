package com.example.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader {

    public interface DownloadCallback {
        void onProgress(int progress);
        void onFinish(File file);
        void onError(Exception e);
    }

    public static void download(
            String fileUrl,
            File outputFile,
            DownloadCallback callback
    ) {

        new Thread(() -> {

            try {

                URL url = new URL(fileUrl);

                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                connection.connect();

                int fileLength = connection.getContentLength();

                InputStream input =
                        connection.getInputStream();

                FileOutputStream output =
                        new FileOutputStream(outputFile);

                byte[] data = new byte[4096];

                long total = 0;

                int count;

                while ((count = input.read(data)) != -1) {

                    total += count;

                    if (fileLength > 0) {

                        int progress =
                                (int) (total * 100 / fileLength);

                        callback.onProgress(progress);
                    }

                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

                callback.onFinish(outputFile);

            } catch (Exception e) {

                callback.onError(e);
            }

        }).start();
    }
}
