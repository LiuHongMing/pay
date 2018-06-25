package com.github.tiger.common.extractor;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liuhongming
 */
public class HtmlMining {

    public static final double NORMALIZED_THRESHOLD = 0.6D;

    public static final int SIBLING_THRESHOLD = 10;

    public static final int MAX_TAG_NODE = 8;

    /**
     * 降噪
     */
    public Element denoise(Element el) {
        int size = el.children().size();
        if (size > 0) {
            List<Node> deleteNodes = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                denoise(deleteNodes, el.child(i));
            }
            for (Node node : deleteNodes) {
                node.remove();
            }
        }
        return el;
    }

    private void denoise(List<Node> deleteNodes, Node thisNode) {
        List<Node> nodes = thisNode.childNodes();
        for (Node node : nodes) {
            String nodeName = node.nodeName();
            if (node instanceof DocumentType
                    || node instanceof Comment
                    || "head".equals(nodeName)
                    || "script".equals(nodeName)
                    || "style".equals(nodeName)
                    || "input".equals(nodeName)
                    || "select".equals(nodeName)
                    || "option".equals(nodeName)
                    || "img".equals(nodeName)
                    || "i".equals(nodeName)
                    ) {
                deleteNodes.add(node);
                continue;
            }
            denoise(deleteNodes, node);
        }
    }

    /**
     * 合并
     */
    public Element merge(Element a, Element b) {
        denoise(a);
        denoise(b);

        HtmlSimilar similar = new HtmlSimilar();
        similar.stm(a, b);
        similar.align(a, b);

        return a;
    }

    /**
     * 数据区域挖掘
     *
     * @param el               挖掘区域
     * @param maxTagNode       最大标签节点数
     * @param similarThreshold 子树相似度阈值
     * @return
     */
    public List<DataRegion> mdr(Element el, int maxTagNode, double similarThreshold) {
        List<DataRegion> dataRegions = new LinkedList<>();

        Elements uncovered = new Elements();

        Elements children = el.children();
        SimilarNeighbors neighbors = neighborCompare(children, maxTagNode, similarThreshold);
        if (neighbors.size() > 0) {
            dataRegions.add(new DataRegion(el, neighbors));
        } else {
            uncovered.addAll(children);
        }

        for (Element child : uncovered) {
            List<DataRegion> drs = mdr(child, maxTagNode, similarThreshold);
            dataRegions.addAll(drs);
        }

        return dataRegions;
    }

    /**
     * 获取节点文本
     */
    public static String getText(Element child, StringBuffer sb) {
        if (hasChildren(child)) {
            Elements elements = child.children();
            if (elements != null && elements.size() > 0) {
                for (Element element : elements) {
                    getText(element, sb);
                }
            }
        } else {
            String text = child.text();
            if (StringUtils.isNoneEmpty(text)) {
                sb.append(text.replaceAll("[\\t\\n]", ""))
                        .append("#");
            }
        }
        return sb.toString();
    }

    /**
     * 获取节点文本
     */
    public static List<Element> getText(Element main, List<Element> ret) {
        if (HtmlMining.hasChildren(main)) {
            Elements elements = main.children();
            if (elements != null && elements.size() > 0) {
                for (Element el : elements) {
                    getText(el, ret);
                }
            }
        }

        if (main.hasText()) {
            String ownText = main.ownText().replaceAll("[ ,:]", "");
            if (StringUtils.isNoneEmpty(ownText)) {
                ret.add(main);
            }
        }

        return ret;
    }

    /**
     * 是否有子节点
     */
    public static boolean hasChildren(Element el) {
        if (el != null) {
            Elements elements = el.children();
            if (elements != null && elements.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 子树对比
     *
     * @param children
     * @param maxTagNode
     * @param threshold
     * @return
     */
    public SimilarNeighbors neighborCompare(Elements children, int maxTagNode, double threshold) {

        SimilarNeighbors neighbors = new SimilarNeighbors();

        int similarSiblingCount = 1;

        int size = children.size();
        Element head = null;
        if (size > 0) {
            head = children.first();
        }
        Element left, right;
        for (int i = 1; i < size; i++) {
            left = head;
            right = children.get(i);
            if (left != null && right != null) {
                if (neighborSimilar(left, right, maxTagNode) >= threshold) {
                    similarSiblingCount += 1;
                    if (similarSiblingCount >= SIBLING_THRESHOLD) {
                        neighbors.put(head);
                    }
                } else {
                    similarSiblingCount = 0;
                }
            }
            head = right;
        }
        return neighbors;
    }

    /**
     * 子树相似度计算
     *
     * @param left
     * @param right
     * @param maxTagNode
     * @return
     */
    public double neighborSimilar(Element left, Element right, int maxTagNode) {
        double sim = 0.0;
        if (HtmlSimilar.nodes(left) >= maxTagNode
                || HtmlSimilar.nodes(right) >= maxTagNode) {
            HtmlSimilar similar = new HtmlSimilar();
            int stm = similar.stm(left, right);
            sim = similar.normalizedStm(left, right, stm);
        }
        return sim;
    }

}
