package com.github.tiger.pay.common.lexicon.analysis;

import org.ansj.library.DicLibrary;
import org.apache.commons.lang3.StringUtils;
import org.nlpcn.commons.lang.util.IOUtil;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author liuhongming
 */
public class Library {

    public static final String DIC;

    static {
        URL dicUrl = Thread.currentThread()
                .getContextClassLoader().getResource("library/default.dic");
        DIC = dicUrl.getFile();
    }

    public static void loadDicLibrary() {
        try {
            loadDicLibrary(DIC);
        } catch (UnsupportedEncodingException
                | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadDicLibrary(String file)
            throws UnsupportedEncodingException, FileNotFoundException {

        if (StringUtils.isEmpty(file)) {
            file = DIC;
        }

        List<String> datas = IOUtil.readFile2List(file, Charset.defaultCharset().name());
        for (String data : datas) {
            String[] arrs = data.split("\t");
            String term = arrs[0];
            String nature = arrs[1];
            String freq = arrs[2];
            DicLibrary.insert(DicLibrary.DEFAULT, term,
                    nature, freq == null ? 0 : Integer.valueOf(freq));
        }
    }

}
