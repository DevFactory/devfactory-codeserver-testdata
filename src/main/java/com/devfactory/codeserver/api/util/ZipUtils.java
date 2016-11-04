package com.devfactory.codeserver.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.apache.commons.io.IOUtils;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ZipUtils {

    public static void unzip(File source, String destination) throws IOException {

        try (ZipFile zipFile = new ZipFile(source)) {

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {

                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(destination, entry.getName());

                if (entry.isDirectory()) {

                    entryDestination.mkdirs();

                } else {

                    entryDestination.getParentFile().mkdirs();
                    InputStream in = zipFile.getInputStream(entry);
                    OutputStream out = new FileOutputStream(entryDestination);
                    IOUtils.copy(in, out);
                    IOUtils.closeQuietly(in);
                    out.close();

                }

            }

        }

    }

    public static void zip(String source, File destination) throws FileNotFoundException, IOException {

        File root = new File(source);

        List<String> fileList = new ArrayList<>();
        populateFileListRecursively(root.getCanonicalPath(), root, fileList);

        byte[] buffer = new byte[1024];

        FileOutputStream fos = new FileOutputStream(destination);
        ZipOutputStream zos = new ZipOutputStream(fos);

        for (String file : fileList) {

            ZipEntry ze = new ZipEntry(file);
            zos.putNextEntry(ze);

            FileInputStream in = new FileInputStream(root + File.separator + file);

            int len;
            while ((len = in.read(buffer)) > 0) {

                zos.write(buffer, 0, len);

            }

            in.close();
        }

        zos.closeEntry();
        zos.close();

    }

    private static void populateFileListRecursively(String root, File node, List<String> fileList) throws IOException {

        if (node.isFile()) {

            String absolutePath = node.getCanonicalPath();
            String relativePath = absolutePath.substring(root.length() + 1, absolutePath.length());

            fileList.add(relativePath);

        }

        if (node.isDirectory()) {

            String[] subNodes = node.list();

            for (String filename : subNodes) {

                populateFileListRecursively(root, new File(node, filename), fileList);

            }

        }

    }

}
