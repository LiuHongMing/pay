package com.github.tiger.test.datamining.similarity.simhash;

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
public interface SimHash {

    /**
     * 分词
     *
     * 赋予权重
     */
    List lexicon(String content);

    /**
     * hash
     */
    long hash(String word);

    /**
     * 加权
     */
    void weighting(int[] vectors);

    /**
     * 合并
     */
    void merge(int[] v1, int[] v2);

    /**
     * 降维
     */
    void reduceDimension();
}
