package com.github.tiger.common.lexicon;

import org.ansj.domain.Term;
import org.ansj.domain.TermNature;
import org.ansj.domain.TermNatures;
import org.ansj.library.DicLibrary;
import org.ansj.util.TermUtil;
import org.apache.commons.lang3.StringUtils;
import org.nlpcn.commons.lang.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author liuhongming
 */
public class Library {

    public static final String DIC;

    public static final String EXTRA;

    static {
        URL library = Thread.currentThread()
                .getContextClassLoader().getResource("library");

        DIC   = library.getFile() + "/default.dic";

        EXTRA = library.getFile() + "/extra.dic";
    }

    static class LibraryReader {

        private final static Logger logger = LoggerFactory
                .getLogger(LibraryReader.class);

        public static BufferedReader getReader(String name) {
            // maven工程修改词典加载方式
            InputStream in = LibraryReader.class.getResourceAsStream("/" + name);
            try {
                return new BufferedReader(new InputStreamReader(in, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.warn("不支持的编码", e);
            }
            return null;
        }

        public static InputStream getInputStream(String name) {
            InputStream in = LibraryReader.class.getResourceAsStream("/" + name);
            return in;
        }
    }


    public static void loadDicLibrary() {
        try {
            loadDicLibrary(DIC);
            loadDicLibrary(EXTRA);
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

        List<String> content = IOUtil.readFile2List(file, Charset.defaultCharset().name());
        for (String data : content) {
            String[] items = data.split("\t");
            String term = items[0];
            String nature = items[1];
            String freq = items[2];
            insertTerm(false, term, nature, freq);
        }
    }

    public static void insertTerm(boolean isNew,
                                  String term, String nature, String freq) {
        DicLibrary.insert(DicLibrary.DEFAULT, term,
                nature, freq == null ? 0 : Integer.valueOf(freq));
        if (isNew) {
            saveNewTerm(term, nature, freq);
        }
    }

    public static void saveNewTerm(String term, String nature, String freq) {
        String content = String.format("%s\t%s\t%s", term, nature, freq);
        File extra = new File(EXTRA);
        if (extra.isDirectory()
                && !extra.exists()) {
            extra.mkdirs();
        } else {
            try {
                extra.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(extra);
            writer.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Term makeTerm(Term from, Term to, String termNature, int frequency) {
        Term newTerm = TermUtil.makeNewTermNum(
                from, to, new TermNatures(
                        new TermNature(termNature, frequency)));
        return newTerm;
    }

}
