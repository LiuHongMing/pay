package com.github.tiger.common.extractor;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author liuhongming
 */
public class HtmlMiningApi {

    public static final String DEFAULT_MINING = "body";

    private DecimalFormat df = new DecimalFormat("#.##");

    HtmlMining hm = new HtmlMining();

    /**
     * HTML模板
     *
     * @return
     */
    public String createTemplate(List<HtmlPage> htmlPages) {
        int MIN_SIZE = 3;
        if (htmlPages == null || htmlPages.size() < MIN_SIZE) {
            throw new IllegalArgumentException("htmlPages is null " +
                    "or less than " + MIN_SIZE);
        }

        HtmlPage target = null;

        int count = htmlPages.size();
        for (int i = 0; i < htmlPages.size(); i++) {
            HtmlPage pageA = htmlPages.get(i);
            for (int j = 0; j < htmlPages.size(); j++) {
                HtmlPage pageB = htmlPages.get(j);
                if (i != j) {
                    pageA.check(pageB);
                }
            }

            double score = ((double) pageA.getSimilarSize() + 1) / count;
            score = Double.valueOf(df.format(score));
            if (score >= 0.5) {
                target = pageA;
                break;
            }
        }

        if (target == null) {
            throw new IllegalArgumentException("htmlPages contains dirty data");
        }

        String ret = target.getBody().toString();

        return ret;
    }

    /**
     * HTML相似验证
     *
     * @return
     */
    public boolean similarCheck(HtmlPage template, HtmlPage htmlPage) {
        if (template == null || htmlPage == null) {
            throw new IllegalArgumentException("template or htmlPage is null");
        }
        return template.check(htmlPage).getSimilarSize() > 0;
    }

    public Map<String, List<String>> extract(String html, String cssQuery) {
        Map<String, List<String>> result = new HashMap<>(16);

        String target = html.replaceAll("\\<!(doctype|DOCTYPE)[^\\<]*\\>?", "");
        Element htmlElement = Jsoup.parse(target);

        Elements elements;
        if (StringUtils.isEmpty(cssQuery)) {

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
