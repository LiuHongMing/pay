package com.github.tiger.test;


import com.github.tiger.common.util.FileUtil;

import java.io.IOException;

public class UnzipTest {

    public static void main(String[] args) throws IOException {
        String fileName = "/Users/liuhongming/Documents/iCoding/workspace/api.zip";
        String unzipDir = "/Users/liuhongming/Documents/iCoding/workspace/temp";

        FileUtil.unzip(fileName, unzipDir);
    }

}
