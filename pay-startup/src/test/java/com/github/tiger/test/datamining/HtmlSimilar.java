package com.github.tiger.test.datamining;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Html相似度
 *
 * @author liuhongming
 */
public class HtmlSimilar {

    private ElementPairMWList elementPairMWList = new ElementPairMWList();

    private DecimalFormat df = new DecimalFormat("#.##");

    /**
     * 简单树匹配（Simple Tree Matching）
     * <p>
     * 计算两子树的节点最大匹配个数
     */
    public int stm(Element a, Element b) {

        String aTag = a.tagName();
        String bTag = b.tagName();

        if (!aTag.equals(bTag)) {
            return 0;
        }

        Elements aChild = a.children();
        Elements bChild = b.children();

        int aChildSize = aChild.size();
        int bChildSize = bChild.size();

        /**
         * 没有子节点时，最大比配中个数等于 1
         */
        if (aChildSize == 0 || bChildSize == 0) {
            return 1;
        }

        /**
         * A、B 第一层子树最大匹配中节点对个数
         */
        int[][] m = new int[aChildSize + 1][bChildSize + 1];
        int[][] w = new int[aChildSize + 1][bChildSize + 1];
        double[][] wn = new double[aChildSize + 1][bChildSize + 1];

        /**
         * 当 A 没有子树时，只有０个匹配
         */
        for (int i = 0; i < aChildSize + 1; i++) {
            m[i][0] = 0;
        }

        /**
         * 当 B 没有子树时，只有０个匹配
         */
        for (int j = 0; j < bChildSize + 1; j++) {
            m[0][j] = 0;
        }

        for (int i = 1; i < aChildSize + 1; i++) {
            for (int j = 1; j < bChildSize + 1; j++) {
                Element x = aChild.get(i - 1);
                Element y = bChild.get(j - 1);
                w[i][j] = stm(x, y);
                wn[i][j] = normalizedStm(x, y, w[i][j]);
                m[i][j] = Math.max(Math.max(m[i][j - 1], m[i - 1][j]),
                        (m[i - 1][j - 1]) + w[i][j]);
            }
        }

        ElementPairMW elementPairMW = new ElementPairMW(a, b, m, w, wn);
        elementPairMWList.addElementPairMW(elementPairMW);

        return m[aChildSize][bChildSize] + 1;
    }

    /**
     * 归一化简单树匹配
     * <p>
     * STM（A， B）/（（nodes（A） + nodes（B））/ 2）
     */
    public double normalizedStm(Element a, Element b, int matchNodes) {
        int aNodes = nodes(a);
        int bNodes = nodes(b);
        double normalizedScore = (double)
                (matchNodes * 2) / (aNodes + bNodes);
        return Double.valueOf(df.format(normalizedScore));
    }

    /**
     * 获取节点个数
     */
    public static int nodes(Element root) {
        if (root == null) {
            return 0;
        } else {
            int num = 1;
            Elements children = root.children();
            for (Element child : children) {
                num += nodes(child);
            }
            return num;
        }
    }

    /**
     * 树对齐
     */
    public void align(Element a, Element b) {
        ElementPairMW elementPairMW = elementPairMWList
                .getElementPairMW(a, b);
        if (elementPairMW != null) {
            Elements aChild = a.children();
            int aChildNum = aChild.size();

            Elements bChild = b.children();
            int bChildNum = bChild.size();

            int[][] m = elementPairMW.getM();
            int[][] w = elementPairMW.getW();

            int maxMatch = m[aChildNum][bChildNum];

            if (maxMatch > 0) {
                /**
                 * 编辑路径
                 */
                List<Station> editPath = backTrace(m, w);
                int size = editPath.size();
                Station now = editPath.get(0);
                for (int i = 1; i < size; i++) {
                    Station next = editPath.get(i);
                    int iMinus = now.getI() - next.getI();
                    int jMinus = now.getJ() - next.getJ();

                    if (iMinus == 0 && jMinus == 1) {
                        /**
                         * 插入
                         */
                        Element thisElement = bChild.get(now.getJ() - 1);
                        String html = thisElement.outerHtml();
                        a.append(html);
                    } else if (iMinus == 1 && jMinus == 0) {
                        /**
                         * 删除
                         */
                         // aChild.get(now.getI() - 1).remove();
                    } else {
                        /**
                         * 相等
                         */
                        align(aChild.get(now.getI() - 1), bChild.get(now.getJ() - 1));
                    }
                    now = next;
                }
            }
        }
    }


    /**
     * 回溯寻找最大编辑路径
     *
     * @param m
     * @param w
     */
    public List<Station> backTrace(int[][] m, int[][] w) {
        List<Station> stationList = new LinkedList<>();
        int i = m.length - 1;
        int j = m[0].length - 1;
        stationList.add(new Station(i, j));

        while (i > 0 || j > 0) {
            if (i > 0) {
                /**
                 * A子树第一层的第i个节点与B子树第一层第j个节点无匹配
                 *
                 * A子树添加节点
                 */
                int up = m[i - 1][j];
                if (up == m[i][j]) {
                    i = i - 1;
                    stationList.add(new Station(i, j));
                    continue;
                }
            }

            if (j > 0) {
                /**
                 * A子树第一层的第i个节点与B子树第一层第j个节点无匹配
                 *
                 * A子树删除节点
                 */
                int left = m[i][j - 1];
                if (left == m[i][j]) {
                    j = j - 1;
                    stationList.add(new Station(i, j));
                    continue;
                }
            }

            if (i > 0 && j > 0) {
                /**
                 * A子树第一层的节点与B子树第一层节点无匹配
                 */
                int upLeft = m[i - 1][j - 1] + w[i][j];
                if (upLeft == m[i][j]) {
                    i = i - 1;
                    j = j - 1;
                    stationList.add(new Station(i, j));
                    continue;
                }
            }
        }

        return stationList;
    }

    public ElementPairMWList getElementPairMWList() {
        return elementPairMWList;
    }

    public void setElementPairMWList(ElementPairMWList elementPairMWList) {
        this.elementPairMWList = elementPairMWList;
    }
}
