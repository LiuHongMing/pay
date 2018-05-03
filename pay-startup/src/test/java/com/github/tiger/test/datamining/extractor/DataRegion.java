package com.github.tiger.test.datamining;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;

/**
 * @author liuhongming
 */
public class DataRegion {

    public static final char SELECTOR_LT = '>';

    public static final char SELECTOR_DOT = '.';

    public static final char SELECTOR_COMMA = ',';

    /**
     * 数据区域
     */
    Element region;

    /**
     * 广义节点
     */
    SimilarNeighbors generalized;

    public DataRegion(Element region, SimilarNeighbors generalized) {
        this.region = region;
        this.generalized = generalized;
    }

    public String cssQuery() {
        StringBuilder sb = new StringBuilder();

        boolean arrive = false;
        Elements parents = region.parents();
        int size = parents.size();
        for (int i = size - 1; i >= 0; i--) {
            Element parent = parents.get(i);
            String tagName = parent.tagName();
            if (HtmlMiningApi.DEFAULT_MINING.equals(tagName)) {
                arrive = true;
                continue;
            }
            if (arrive) {
                sb.append(getCssStyle(parent, false));
            }
        }

        sb.append(getCssStyle(region, true))
                .append(generalized.getCssStyle());

        String cssQuery = sb.toString().trim();
        return cssQuery;
    }

    public String getCssStyle(Element el, boolean needClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(el.tagName());

        if (needClass) {
            Set<String> classNameSet = el.classNames();
            if (classNameSet != null && classNameSet.size() > 0) {
                Object[] classNameArray = classNameSet.toArray();
                String className = (String) classNameArray[0];
                if (StringUtils.isNoneEmpty(className)) {
                    sb.append(SELECTOR_DOT).append(className);
                }
            }
        }

        sb.append(SELECTOR_LT);

        return sb.toString();
    }

    public Elements select(String cssQuery) {
        if (cssQuery == null) {
            cssQuery = cssQuery();
        }

        Element body = null;

        Elements parents = region.parents();
        int size = parents.size();
        for (int i = size - 1; i > 0; i--) {
            String tagName = parents.get(i).tagName();
            if (HtmlMiningApi.DEFAULT_MINING.equals(tagName)) {
                body = parents.get(i);
                break;
            }
        }

        return body.select(cssQuery);
    }


    @Override
    public String toString() {
        return "DataRegion{" +
                "region=" + region +
                ", generalized=" + generalized +
                '}';
    }
}
