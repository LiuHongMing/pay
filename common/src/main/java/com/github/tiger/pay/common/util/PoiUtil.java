package com.github.tiger.pay.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.POITextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 功能：Office文件操作
 * <p>
 * 描述：涵盖Word文档内容提取
 *
 * @author liuhongming
 */
public class PoiUtil {

    private static final Logger logger = LoggerFactory.getLogger(PoiUtil.class);

    /**
     * 获取word文本内容
     *
     * @param path 文件路径
     * @return
     */
    public static String getWordText(String path) {
        String body = null;
        try {
            body = WordTemplate.build(path).getText();
        } catch (IOException e) {
            logger.error(
                    String.format("An exception occured " +
                            "while extract text from the word: %s", path), e);
        }
        return body;
    }

    static class WordTemplate {

        static final String DOC = "doc";

        static final String DOCX = "docx";

        private POITextExtractor textExtractor;

        private WordTemplate(POITextExtractor textExtractor) {
            this.textExtractor = textExtractor;
        }

        String getText() {
            return textExtractor.getText();
        }

        public static WordTemplate build(String path) throws IOException {
            WordTemplate template;

            File file = new File(path);
            FileInputStream fis = FileUtils.openInputStream(file);
            String extension = FilenameUtils.getExtension(path);

            switch (extension.toLowerCase()) {
                case DOC:
                    template = new WordTemplate(new WordExtractor(fis));
                    break;
                case DOCX:
                default:
                    XWPFDocument xwpfDocument = new XWPFDocument(fis);
                    template = new WordTemplate(new XWPFWordExtractor(xwpfDocument));
            }

            return template;
        }

    }
}
