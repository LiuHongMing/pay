package com.github.tiger.test.datamining.extractor;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * @author liuhongming
 */
public class DataRecord {

    private Element root;

    private List<Element> textRecords;

    private List<Element> linkRecords;

	private Map<Element, String> absPathMap = new LinkedHashMap<>();

    public DataRecord(Element root, List<Element> textRecords) {
        this.root = root;
        this.textRecords = textRecords;
        this.linkRecords = new ArrayList<Element>();
        editAbsPath(this.root);
    }

    public int getTextCount() {
        return textRecords.size();
    }

    public List<Element> getTextRecords() {
        return textRecords;
    }
    
    public List<Element> getLinkRecords() {
		return linkRecords;
	}

	public void editAbsPath(Element target) {
        if (HtmlMining.hasChildren(target)) {
            Elements children = target.children();
            int size = children.size();
            if (children != null && size > 0) {
                for (int i = 0, j = 1; i < size; i++, j++) {
                    Element child = children.get(i);
                    if (child.tagName().equals("a")) {
                    	linkRecords.add(child);
                    }
                    Element parent = child.parent();
                    String absPath = absPathMap.get(parent);
                    StringBuilder sb = new StringBuilder();
                    if (absPath != null) {
                        sb.append(absPath).append(DataRegion.SELECTOR_LT);
                    }
                    sb.append(getCssStyle(child, j, true, false));
                    absPathMap.put(child, sb.toString());
                    editAbsPath(child);
                }
            }
        }
    }

    public String getAbsPath(Element el) {
        return absPathMap.get(el);
    }

    public String getCssStyle(Element el, Integer index, boolean needClass, boolean needDelimited) {
        StringBuilder sb = new StringBuilder();
        sb.append(el.tagName());

        if (needClass) {
            Set<String> classNameSet = el.classNames();
            if (classNameSet != null && classNameSet.size() > 0) {
                Object[] classNameArray = classNameSet.toArray();
                String className = (String) classNameArray[0];
                if (StringUtils.isNoneEmpty(className)) {
                    sb.append(DataRegion.SELECTOR_DOT).append(className);
                }
            }
        }

        if (index != null) {
            sb.append("_").append(index);
        }

        if (needDelimited) {
            sb.append(DataRegion.SELECTOR_LT);
        }

        return sb.toString();
    }

}
