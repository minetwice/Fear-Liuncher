package com.example.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {

    public static void extract(
            File zipFile,
            File targetDirectory
    ) {

        try {

            ZipInputStream zis =
                    new ZipInputStream(
                            new FileInputStream(zipFile)
                    );

            ZipEntry entry;

            byte[] buffer = new byte[4096];

            while ((entry = zis.getNextEntry()) != null) {

                File newFile =
                        new File(
                                targetDirectory,
                                entry.getName()
                        );

                if (entry.isDirectory()) {

                    newFile.mkdirs();

                } else {

                    new File(
                            newFile.getParent()
                    ).mkdirs();

                    FileOutputStream fos =
                            new FileOutputStream(newFile);

                    int len;

                    while ((len = zis.read(buffer)) > 0) {

                        fos.write(buffer, 0, len);
                    }

                    fos.close();
                }
            }

            zis.closeEntry();
            zis.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
