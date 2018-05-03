package com.github.tiger.test.datamining.similarity;

/**
 * 余弦距离
 */
public interface CosineDistance {

    /**
     * 分子
     */
    double numerinator();

    /**
     * 分母
     */
    double demonator();

    /**
     * 分值
     */
    double rating();
}
