package com.github.tiger.test;

import com.github.tiger.pay.common.util.FileUtil;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnzipTest {

    public static void main(String[] args) throws IOException {
        String fileName = "/Users/liuhongming/Documents/iCoding/workspace/api.zip";
        String unzipDir = "/Users/liuhongming/Documents/iCoding/workspace/temp";

        FileUtil.unzip(fileName, unzipDir);
    }

}
