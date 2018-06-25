package com.github.tiger.common.extractor;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author liuhongming
 */
public class SimilarNeighbors extends LinkedList<Element> {

    private Set<String> tagStyle = new HashSet();

    public String getCssStyle() {
        StringBuilder sb = new StringBuilder();

        Object[] styles = tagStyle.toArray();
        if (styles != null && styles.length > 0) {
            for (Object style : styles) {
                sb.append(style).append(DataRegion.SELECTOR_COMMA);
            }
        }

        String cssStyle = sb.toString();
        if (StringUtils.isNoneEmpty(cssStyle)) {
            char ch = cssStyle.charAt(cssStyle.length() - 1);
            if (ch == DataRegion.SELECTOR_COMMA) {
                cssStyle = cssStyle.substring(0, cssStyle.length() - 1);
            }
        }

        return cssStyle;
    }

    public void put(Element el) {
        add(el);

        String tagName = el.tagName();
        String className = null;

        Set<String> classNameSet = el.classNames();
        if (classNameSet != null && classNameSet.size() > 0) {
            Object[] classNameArray = classNameSet.toArray();
            className = (String) classNameArray[0];
        }

        String style = tagName;
        if (className != null) {
            style += DataRegion.SELECTOR_DOT + className;
        }

        tagStyle.add(style);
    }

}
