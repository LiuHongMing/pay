package com.github.tiger.common.extractor;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author liuhongming
 */
public class HtmlMiningApi {

    public static final String DEFAULT_MINING = "body";

    public Map<String, List<String>> extract(String html, String cssQuery) {
        Map<String, List<String>> result = new HashMap<>(16);

        String target = html.replaceAll("\\<!(doctype|DOCTYPE)[^\\<]*\\>?", "");
        Element htmlElement = Jsoup.parse(target);

        Elements elements;
        if (StringUtils.isEmpty(cssQuery)) {
            HtmlMining hm = new HtmlMining();

            Element el = hm.denoise(htmlElement);
            el = el.select(DEFAULT_MINING).first();
            List<DataRegion> dataRegions = hm.mdr(el, HtmlMining.MAX_TAG_NODE,
                    HtmlMining.NORMALIZED_THRESHOLD);

            for (DataRegion dr : dataRegions) {
                elements = dr.select(dr.cssQuery());
                List<String> jsonData = toJsonList(elements);
                if (jsonData.size() > 0) {
                    result.put(dr.cssQuery(), jsonData);
                }
            }
        } else {
            elements = htmlElement.select(cssQuery);
            List<String> jsonData = toJsonList(elements);
            result.put(cssQuery, jsonData);
        }

        return result;
    }

    public List<String> toJsonList(Elements elements) {
        int maxTextItem = 60;
        boolean moreItem = false;

        DataRecords dataRecords = new DataRecords();
        for (Element element : elements) {
            if (HtmlSimilar.nodes(element) >= HtmlMining.MAX_TAG_NODE) {
                List<Element> textElements = HtmlMining.getText(element, new LinkedList<>());

                if (textElements.size() >= maxTextItem) {
                    moreItem = true;
                    break;
                }

                DataRecord dataRecord = new DataRecord(element, textElements);
                dataRecords.add(dataRecord);
            }
        }

        if (moreItem) {
            dataRecords.clear();
        }

        return dataRecords.toJsonList();
    }
}
