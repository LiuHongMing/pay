package com.github.tiger.common.extractor.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuhongming
 */
public class Templates {

    public static String loadContent(String path) throws IOException {
        return loadContent(path, Charset.defaultCharset().name());
    }

    public static String loadContent(String path, String encode) throws IOException {
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(path), Charset.forName(encode));
        BufferedReader bufferedReader = new BufferedReader(reader);

        String content = "";
        try {
            for (; ; ) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                content += line;
            }
        } finally {
            bufferedReader.close();
        }

        return content;
    }

    public static List<String> loadContents(String path) throws IOException {
        return loadContents(path, Charset.defaultCharset().name());
    }

    public static List<String> loadContents(String path, String encode) throws IOException {
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(path), Charset.forName(encode));
        BufferedReader bufferedReader = new BufferedReader(reader);

        List<String> contentList = new ArrayList<>();
        try {
            for (; ; ) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                contentList.add(line);
            }
        } finally {
            bufferedReader.close();
        }

        return contentList;
    }
}