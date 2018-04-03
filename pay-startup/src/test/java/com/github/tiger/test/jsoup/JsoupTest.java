package com.github.tiger.test.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class JsoupTest {

    String html = "<html>\n" +
            " <head> \n" +
            "  <title>First</title> \n" +
            " </head> \n" +
            " <body> \n" +
            "  <!-- haha -->" +
            "  <div class=\"list\" id=\"hello\"> \n" +
            "   <div class=\"item\"> \n" +
            "    <p><span>Parsed First HTML into a doc.</span></p> \n" +
            "   </div>  \n" +
            "   <div class=\"item\"> \n" +
            "    <p><span>Parsed First HTML into a doc.</span></p> \n" +
            "   </div>  \n" +
            "  </div>\n" +
            "  <div class=\"item\"> \n" +
            "    <p><span>Parsed First HTML into a doc.</span></p> \n" +
            "   </div>  \n" +
            " </body>\n" +
            "</html>";

    String html2 = "<html>\n" +
            " <head> \n" +
            "  <title>Second</title> \n" +
            " </head> \n" +
            " <body> \n" +
            "  <div class=\"list\"> \n" +
            "   <div class=\"item\"> \n" +
            "    <p><span>Parsed Second HTML into a doc.</span></p> \n" +
            "   </div>  \n" +
            "  </div>\n" +
            " </body>\n" +
            "</html>";

    Document document = Jsoup.parse(html);

    @Test()
    public void testTraverse() {
        Elements elements = document.select(".item");
        for(Element element : elements) {
            System.out.println(element);
            System.out.println("----------");
        }

    }

    @Test()
    public void testAppend() {
        Element span = document.createElement("span");
        Element p = document.select("div > p").first();
        p.appendChild(span);
        System.out.println(document);
    }

    @Test()
    public void testDelete() {
        Element el = document.select("div.list").first();
        el.removeAttr("id");
        System.out.println(el);
    }
}
