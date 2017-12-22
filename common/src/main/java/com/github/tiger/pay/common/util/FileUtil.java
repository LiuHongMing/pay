package com.github.tiger.pay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 功能：文件操作工具类
 * <p>
 * 描述：涵盖文件操作的实现：
 * 1. 创建目录
 * 2. Zip文件的压缩与解压缩
 *
 * @author liuhongming
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static final int BUFFER_SIZE = 2048;

    /**
     * 解压Zip文件
     *
     * @param fileName
     * @param unzipDir
     */
    public static void unzip(String fileName, String unzipDir) {
        BufferedInputStream bis;
        BufferedOutputStream dest;
        FileOutputStream fos;
        ZipEntry zipEntry;
        try {
            mkdirs(unzipDir);

            ZipFile zipFile = new ZipFile(fileName);
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                zipEntry = (ZipEntry) entries.nextElement();

                String entryName = zipEntry.getName();
                String unzipPath = String.join("/", unzipDir, entryName);

                if (zipEntry.isDirectory()) {
                    mkdirs(unzipPath);
                } else {
                    int count;
                    byte[] data = new byte[BUFFER_SIZE];
                    bis = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                    fos = new FileOutputStream(unzipPath);
                    dest = new BufferedOutputStream(fos, BUFFER_SIZE);
                    while ((count = bis.read(data, 0, BUFFER_SIZE)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                    bis.close();
                }
            }

        } catch (IOException e) {
            logger.error(
                    String.format("An exception occured " +
                            "while extracting the file: %s", fileName), e);
        }
    }

    /**
     * 创建目录
     *
     * @param path
     */
    public static boolean mkdirs(String path) {
        boolean ok = false;
        File file = new File(path);
        if (!file.exists()) {
            try {
                ok = file.mkdirs();
            } catch (Exception e) {
                logger.error(
                        String.format("An exception occured " +
                                "while creating the directry: %s", path), e);
            }
        }
        return ok;
    }

}
