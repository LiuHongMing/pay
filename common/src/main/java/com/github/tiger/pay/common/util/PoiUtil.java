package com.github.tiger.pay.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.poi.POITextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 功能：MS-Office文件操作
 * <p>
 * 描述：涵盖Word文档内容提取
 *
 * @author liuhongming
 */
public class PoiUtil {

    private static final Logger logger = LoggerFactory.getLogger(PoiUtil.class);

    private static final String EMPTY = "";

    /**
     * 获取word文本内容
     *
     * @param path 文件路径
     * @return
     */
    public static String getWordText(String path) {
        WordTemplate wordTemplate = null;
        try {
            wordTemplate = WordTemplate.create(path);
            return wordTemplate.getText();
        } catch (IOException e) {
            logger.error(
                    String.format("An exception occured " +
                            "while extract text: %s", path), e);
        } finally {
            if (wordTemplate != null) {
                try {
                    wordTemplate.close();
                } catch (IOException e) {
                    logger.error(
                            String.format("An exception occured " +
                                    "while closed: %s", path), e);
                }
            }
        }
        return EMPTY;
    }

    /**
     * word模板类，封装word操作方法
     */
    static class WordTemplate {

        private POITextExtractor textExtractor;

        private WordTemplate(POITextExtractor textExtractor) {
            this.textExtractor = textExtractor;
        }

        String getText() throws IOException {
            return textExtractor.getText();
        }

        void close() throws IOException {
            textExtractor.close();
        }

        public static WordTemplate create(String path) throws IOException {
            WordTemplate template = null;

            File file = new File(path);
            FileInputStream fis = FileUtils.openInputStream(file);

            POITextExtractor extractor;
            if (FileMagic.valueOf(fis) == FileMagic.OLE2) {
                template = new WordTemplate(new WordExtractor(fis));
            } else if (FileMagic.valueOf(fis) == FileMagic.OOXML) {
                extractor = new XWPFWordExtractor(new XWPFDocument(fis));
                template = new WordTemplate(extractor);
            }

            return template;
        }
    }
}
