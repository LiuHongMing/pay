package com.github.tiger.pay.test;

import com.github.tiger.pay.common.util.PoiUtil;

import java.io.IOException;

public class WordExtractorTest {

    public static void main(String[] args) throws IOException {
        String hwpfWord = "/Users/liuhongming/Documents/iCoding/workspace/test/hwpf.doc";
        System.out.println(PoiUtil.getWordText(hwpfWord));

        String xwpfWord = "/Users/liuhongming/Documents/iCoding/workspace/test/xwpf.docx";
        System.out.println(PoiUtil.getWordText(xwpfWord));
    }

}
