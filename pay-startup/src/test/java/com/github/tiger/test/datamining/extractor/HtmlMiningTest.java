package com.github.tiger.test.datamining.extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HtmlMiningTest {

    String html1 = "<!doctype html><html>" +
            " <head>" +
            "  <title>First</title>" +
            " </head>" +
            " <body>" +
            "  <div class=\"list\">" +
            "   <div class=\"item1 item2\">" +
            "    <p><span>One</span></p>" +
            "   </div>" +
            "   <table>" +
            "    <tr><td></td></tr>" +
            "   </table>" +
            "   <div class=\"item2\">" +
            "    <p><span>1</span></p>" +
            "   </div> " +
            "   <div class=\"item2\">" +
            "    <p><span>2</span></p>" +
            "   </div> " +
            "   <div class=\"item2\">" +
            "    <p><span>3</span></p>" +
            "   </div> " +
            "   <div class=\"item2\">" +
            "    <p><span>4</span></p>" +
            "   </div> " +
            "   <div class=\"item2\">" +
            "    <p><span>5</span></p>" +
            "   </div> " +
            "   <div class=\"item2\">" +
            "    <p><span>6</span></p>" +
            "   </div> " +
            "   <div class=\"item2\">" +
            "    <p><span>7</span></p>" +
            "   </div> " +
            "   <div class=\"item2\">" +
            "    <p><span>8</span></p>" +
            "   </div> " +
            "   <div class=\"item2\">" +
            "    <p><span>9</span></p>" +
            "   </div> " +
            "   <div class=\"item2\">" +
            "    <p><span>10</span></p>" +
            "   </div> " +
            "  </div>" +
            "  <div class=\"foot11\">" +
            "    <p>First HTML foot.</p>" +
            "  </div>" +
            " </body>" +
            "</html>";

    String html2 = "<html>" +
            " <head>" +
            "  <title>Second</title>" +
            " </head>" +
            " <body>" +
            "  <div class=\"list\">" +
            "   <div class=\"item1\">" +
            "    <p><span>One</span></p>" +
            "    <span>One</span>" +
            "   </div>" +
            "  </div>" +
            "  <div class=\"foot22\">" +
            "    <p>Second HTML foot.</p>" +
            "  </div>" +
            " </body>" +
            "</html>";

    /**
     * 简单树匹配测试
     */
    @Test()
    public void testMining() {
        Element root = Jsoup.parse(html2);
//
//        Element a = root.select("tr>td").first();
//
//        Elements parents = a.parents();
//        int size = parents.size();
//
//        for (int i = size - 1; i >= 0; i--) {
//            System.out.println(parents.get(i).tagName());
//        }

    }

    /**
     * 简单树匹配测试
     */
    @Test()
    public void testMining2() throws IOException {
        String tlp1 = "/Users/liuhongming" +
                "/Documents/iCoding/workspace/test/record.htm";
        String html1 = loadContent(tlp1);

        Elements all = Jsoup.parse(html1).select("div");

        List<String> jsonData = new ArrayList<>();

        for (Element element : all) {
//            Map<String, String> jsonMap = new LinkedHashMap<>(16);
//
//            List<Element> textElements = new ArrayList<>();
//            HtmlMining.getText(element, textElements);
//
//            Map<Element, List<String>> range = HtmlMiningApi.range(textElements);
//
//            int i = 1;
//            Iterator<Element> keyIterator = range.keySet().iterator();
//            while (keyIterator.hasNext()) {
//                Element key = keyIterator.next();
//                List<String> values = range.get(key);
//
//                jsonMap.put(String.valueOf(i),
//                        String.join(String.valueOf(DataRegion.SELECTOR_COMMA), values));
//
//                i++;
//            }
//
//            jsonData.add(JSON.toJSONString(jsonMap));
        }

        System.out.println(jsonData);

    }

    @Test()
    public void testMining3() throws IOException {
        String tlp1 = "/Users/liuhongming" +
                "/Documents/iCoding/workspace/test/liepin1.htm";
        String html1 = loadContent(tlp1);

        HtmlMiningApi hma = new HtmlMiningApi();
//        Map<String,List<String>> result = hma.extract(html1, "div>div>form>div>table>tbody>tr.bg0,tr.bg1");
        Map<String, List<String>> result = hma.extract(html1, "");
        System.out.println(result);
    }

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
}
