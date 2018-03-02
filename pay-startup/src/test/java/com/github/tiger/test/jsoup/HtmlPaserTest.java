package com.github.tiger.test.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

public class HtmlPaserTest {

    @Test()
    public void testSimple() {
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        Document document = Jsoup.parse(html);
        Elements elements = document.select("body > p");
        for (Element element : elements) {
            System.out.println(element.text());
        }
    }

}
