package com.github.tiger.test.datamining;

import org.jsoup.nodes.Element;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liuhongming
 */
public class ElementPairMWList {

    List<ElementPairMW> elementPairMWList;

    public ElementPairMWList() {
        this.elementPairMWList = new LinkedList<>();
    }

    public ElementPairMW getElementPairMW(Element a, Element b) {
        for (ElementPairMW elementPairMW : elementPairMWList) {
            if (elementPairMW.getA() == a
                    && elementPairMW.getB() == b) {
                return elementPairMW;
            }
        }
        return null;
    }

    public void addElementPairMW(ElementPairMW pairMW) {
        elementPairMWList.add(pairMW);
    }
}
