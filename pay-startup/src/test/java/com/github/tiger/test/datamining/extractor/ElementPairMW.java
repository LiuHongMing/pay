package com.github.tiger.test.datamining;

import org.jsoup.nodes.Element;

/**
 * @author liuhongming
 */
public class ElementPairMW {

    private Element a;

    private Element b;

    private int[][] m;

    private int[][] w;

    private double[][] wn;

    public ElementPairMW(Element a, Element b, int[][] m, int[][] w, double[][] wn) {
        this.a = a;
        this.b = b;
        this.m = m;
        this.w = w;
        this.wn = wn;
    }

    public Element getA() {
        return a;
    }

    public void setA(Element a) {
        this.a = a;
    }

    public Element getB() {
        return b;
    }

    public void setB(Element b) {
        this.b = b;
    }

    public int[][] getM() {
        return m;
    }

    public void setM(int[][] m) {
        this.m = m;
    }

    public int[][] getW() {
        return w;
    }

    public void setW(int[][] w) {
        this.w = w;
    }

    public double[][] getWn() {
        return wn;
    }

    public void setWn(double[][] wn) {
        this.wn = wn;
    }
}
