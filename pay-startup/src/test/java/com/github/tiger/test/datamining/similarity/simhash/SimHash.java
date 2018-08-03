package com.github.tiger.test.datamining.similarity.simhash;

import org.nlpcn.commons.lang.finger.SimHashService;

import java.util.List;

/**
 * 文本相似度算法：SimHash
 * <p>
 * 步骤：
 * 1、分词
 * 2、hash
 * 3、加权
 * 4、合并
 * 5、降维
 */
public class SimHash {

    private int bithash = 64;

    /**
     * 分词
     */
    List tokenizer(String content) {
        return null;
    }

    /**
     * hash
     */
    long hash(String word) {
        return 0;
    }

    /**
     * 加权
     */
    void weight(int[] vectors) {

    }

    /**
     * 累加
     */
    void accumulate(int[] v1, int[] v2) {

    }

    /**
     * 降维
     */
    void reduceDimension() {

    }

    /**
     * 指纹
     */
    void fingerprint() {

    }

    public static void main(String[] args) {
        SimHashService shs = new SimHashService();
        System.out.println(shs.fingerprint("北京大学"));
        System.out.println(shs.fingerprint("北大"));
    }

}
