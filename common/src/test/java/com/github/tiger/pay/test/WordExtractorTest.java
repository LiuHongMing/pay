package com.github.tiger.pay.test;

import com.aspose.words.FileFormatInfo;
import com.aspose.words.FileFormatUtil;
import com.aspose.words.LoadFormat;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFOldDocument;
import org.apache.poi.hwpf.converter.WordToFoUtils;
import org.apache.poi.hwpf.converter.WordToTextConverter;
import org.apache.poi.hwpf.extractor.Word6Extractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class WordExtractorTest {

    public static void main(String[] args) throws IOException {
//        String hwpfWord = "/Users/liuhongming/Documents/iCoding/workspace/test/hwpf.doc";
//        System.out.println(PoiUtil.getWordText(hwpfWord));
//
//        String xwpfWord = "/Users/liuhongming/Documents/iCoding/workspace/test/xwpf.docx";
//        System.out.println(PoiUtil.getWordText(xwpfWord));

    }

    @Test
    public void testHWPFDocument() throws Exception {
        String hwpfWord = "/Users/liuhongming/Documents/iCoding/workspace/test/hwpf.doc";
        FileInputStream fis = FileUtils.openInputStream(new File(hwpfWord));
        WordExtractor wordExtractor = new WordExtractor(fis);
        wordExtractor.getText();
    }

    @Test
    public void testUnknowDocument() throws Exception {
        String unkonw = "/Users/liuhongming/Documents/iCoding/workspace/test/简历-刘洪铭.doc";
        FileInputStream fis = FileUtils.openInputStream(new File(unkonw));

        byte[] ole2Magic = {-48, -49, 17, -32, -95, -79, 26, -31};
        byte[] raw = IOUtils.toByteArray(fis);
        byte[] cover = new byte[raw.length];
        System.arraycopy(ole2Magic, 0, cover, 0, 8);
        System.arraycopy(raw, 8, cover, 8, raw.length - 8);

        cover[30] = 12;
        ByteArrayInputStream bais = new ByteArrayInputStream(cover);

        WordExtractor wordExtractor = new WordExtractor(bais);
        wordExtractor.getText();

    }

    @Test
    public void testXWPFDocument() throws IOException {
        String xwpfWord = "/Users/liuhongming/Documents/iCoding/workspace/test/xwpf.docx";
        FileInputStream fis = FileUtils.openInputStream(new File(xwpfWord));
        XWPFDocument xwpfDocument = new XWPFDocument(fis);
        XWPFWordExtractor wordExtractor = new XWPFWordExtractor(xwpfDocument);
        System.out.println(wordExtractor.getText());
    }

}
