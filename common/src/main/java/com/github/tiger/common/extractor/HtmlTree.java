package com.github.tiger.common.extractor;

import org.jsoup.nodes.Node;

import java.util.List;

/**
 * @author liuhongming
 */
public class HtmlTree {

    private Node node;

    private int depth;

    public HtmlTree(Node node) {
        this.node = node;
    }

    private void computeDepth(Node target, int accum) {
        List<Node> nodeList = target.childNodes();
        if (nodeList.size() == 0) {
            depth = Math.max(depth, accum);
        } else {
            for (Node node : nodeList) {
                computeDepth(node, accum + 1);
            }
        }
    }

    public int depth() {
        computeDepth(node, 0);
        return depth;
    }

}
