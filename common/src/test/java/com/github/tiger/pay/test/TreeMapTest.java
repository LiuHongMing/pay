package com.github.tiger.pay.test;

import org.junit.Test;

import java.util.TreeMap;

public class TreeMapTest {

    @Test
    public void testBuild() {
        TreeMap treeMap = new TreeMap();
        treeMap.put(3, "3");
        treeMap.put(1, "1");
        treeMap.put(2, "2");
        System.out.println(treeMap);
    }

}
