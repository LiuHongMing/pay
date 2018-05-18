package com.github.tiger.test.trie.darts;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DoubleArrayTrieTest {

    @Test
    public void testBuild() {
        List<String> kws = new ArrayList<>();
        kws.add("中国");
        kws.add("中华");
        kws.add("中央");
        kws.add("中意");
        DoubleArrayTrie dat = new DoubleArrayTrie();
        dat.build(kws);
    }

}
