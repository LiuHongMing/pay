package com.github.tiger.test.datamining;

import com.alibaba.fastjson.JSON;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuhongming
 */
public class DataRecords {

    private List<DataRecord> dataRecords;

    private int maxTextCount = 0;

    public DataRecords() {
        this.dataRecords = new ArrayList<>();
    }

    public void add(DataRecord record) {
        maxTextCount = Math.max(maxTextCount,
                record.getTextCount());
        dataRecords.add(record);
    }

    public void clear() {
        dataRecords.clear();
    }

    public List<String> toJsonList() {
        List<String> jsonData = new ArrayList<>();

        for (DataRecord dr : this.dataRecords) {
            Map<String, String> jsonMap = new LinkedHashMap<>(16);
            List<Element> tr = dr.getTextRecords();
            int size = tr.size();
            for (int j = 0; j < size; j++) {
                Element el = tr.get(j);
                String tagName = el.tagName();
                String absPath = dr.getAbsPath(el);
                if (tagName.equals("a")) {
                    boolean hasHref = el.hasAttr("href");
                    String hrefValue = el.attr("href");
                    if (hasHref && !hrefValue.contains("javascript")) {
                        jsonMap.put(absPath,
                                String.format("%s(%s)", el.ownText(), hrefValue));
                    }
                } else {
                    jsonMap.put(absPath, el.ownText());
                }
            }
            jsonData.add(JSON.toJSONString(jsonMap));
        }

        return jsonData;
    }
}
