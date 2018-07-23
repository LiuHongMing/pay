package com.github.tiger.common.extractor;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuhongming
 */
public class HtmlPage extends ArrayList<Element>
        implements Comparable<HtmlPage> {

    private static HtmlMining hm = new HtmlMining();

    private String id;

    private Element body;

    public HtmlPage(String thisHtml) {
        this.id = RandomStringUtils.randomNumeric(6);
        process(thisHtml);
    }

    private void process(String thisHtml) {
        String target = thisHtml.replaceAll("\\<!(doctype|DOCTYPE)[^\\<]*\\>?", "");
        Element htmlElement = Jsoup.parse(target);

        Element el = hm.denoise(htmlElement);
        body = el.select(HtmlMiningApi.DEFAULT_MINING).first();
        deleteText(body);
    }

    public String getId() {
        return id;
    }

    public Element getBody() {
        return body;
    }

    public Element deleteText(Element el) {
        int size = el.children().size();
        if (size > 0) {
            List<Node> deleteNodes = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                deleteText(deleteNodes, el.child(i));
            }
            for (Node node : deleteNodes) {
                node.remove();
            }
        }
        return el;
    }

    private void deleteText(List<Node> deleteNodes, Node thisNode) {
        List<Node> nodes = thisNode.childNodes();
        for (Node node : nodes) {
            if (node instanceof TextNode) {
                deleteNodes.add(node);
                continue;
            }
            deleteText(deleteNodes, node);
        }
    }

    public HtmlPage check(HtmlPage thatHtmlPage) {
        if (thatHtmlPage == null) {
            throw new IllegalArgumentException("thatHtmlPage is null");
        }
        Element thatBody = thatHtmlPage.getBody();
        if (similar(thatBody)) {
            add(thatBody);
        }
        return this;
    }

    public int getSimilarSize() {
        return size();
    }

    private boolean similar(Element thatBody) {
        if (thatBody == null) {
            throw new IllegalArgumentException("thatBody is null");
        }
        HtmlSimilar similar = new HtmlSimilar();
        int stm = similar.stm(this.getBody(), thatBody);
        double sim = similar.normalizedStm(getBody(), thatBody, stm);
        return sim > HtmlMining.NORMALIZED_THRESHOLD;
    }

    @Override
    public int compareTo(HtmlPage thatHtmlPage) {
        int thisSimilarSize = getSimilarSize();
        int thatSimilarSize = thatHtmlPage.getSimilarSize();

        if (thisSimilarSize == thatSimilarSize) {
            return 0;
        }
        return thisSimilarSize > thatSimilarSize ? 1 : -1;
    }

    @Override
    public String toString() {
        return "HtmlPage{" + "id='" + id + '\'' + ", body=" + body + '}';
    }
}
