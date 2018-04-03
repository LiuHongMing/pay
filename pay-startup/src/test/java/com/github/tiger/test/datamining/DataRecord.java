package com.github.tiger.test.datamining;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * @author liuhongming
 */
public class DataRecord {

    private Element root;

    private List<Element> textRecords;

    public DataRecord(Element root, List<Element> textRecords) {
        this.root = root;
        this.textRecords = textRecords;
    }

    public int getTextCount() {
        return textRecords.size();
    }

    public List<Element> getTextRecords() {
        return textRecords;
    }

}
