package com.github.tiger.test.datamining.similarity.cosine;

/**
 * 余弦距离
 */
public interface CosineDistance {

    /**
     * 分子
     */
    double numerator();

    /**
     * 分母
     */
    double denominator();

    /**
     * 评估
     */
    double rating(double numerator, double denominator);
}
