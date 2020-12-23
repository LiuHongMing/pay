package com.github.tiger.common.extractor.utils;

import com.github.tiger.common.lexicon.Analysis;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特征相似计算工具类
 *
 * @author yu.xiao
 */
public class FeatureSimilarUtil {

    public static void main(String[] args) {

    }

    /**
     * 词性特征比率计算
     *
     * @param list 数据源Map<属性标识，数据>
     * @return <属性标识, Map<词性, 词性占比>>
     */
    public Map<String, Map<String, Double>> getNaturesProbabiliMap(
            List<Map<String, String>> list) {
        double limit = 0.005;
        double minFeatureCount = list.size() / 10;
        // 记录每个属性的个数
        Map<String, Integer> featureCountMap = new HashMap<>();
        // 记录每个属性的词性总数
        Map<String, Double> featureNaturesCountMap = new HashMap<>();
        Map<String, Map<String, Double>> featureMap = new HashMap<>();
        Map<String, Map<String, Double>> countMap = new HashMap<>();
        if (list != null && list.size() > 0) {
            for (Map<String, String> map : list) {
                for (String key : map.keySet()) {
                    if (!countMap.containsKey(key)) {
                        featureCountMap.put(key, 0);
                        featureNaturesCountMap.put(key, 0d);
                        countMap.put(key, new HashMap<String, Double>());
                    }
                    int featureCount = featureCountMap.get(key) + 1;
                    featureCountMap.put(key, featureCount);
                    List<String> natures = Analysis.getDicAnalysisNatures(map
                            .get(key));
                    for (String nature : natures) {
                        if (!countMap.get(key).containsKey(nature)) {
                            countMap.get(key).put(nature, 0d);
                        }
                        double count = countMap.get(key).get(nature) + 1;
                        countMap.get(key).put(nature, count);
                        double featureNaturesCount = featureNaturesCountMap
                                .get(key) + 1;
                        featureNaturesCountMap.put(key, featureNaturesCount);
                    }
                }
            }

            for (String attr : countMap.keySet()) {
                // 如果这个属性的数据数小于等于属性数量限制 则视为干扰数据不参与计算
                if (featureCountMap.get(attr) <= minFeatureCount) {
                    continue;
                }
                featureMap.put(attr, new HashMap<String, Double>());
                for (String key : countMap.get(attr).keySet()) {

                    double result = new BigDecimal(countMap.get(attr).get(key)
                            / featureNaturesCountMap.get(attr)).setScale(4,
                            BigDecimal.ROUND_HALF_UP).doubleValue();
                    if (result > limit) {
                        featureMap.get(attr).put(key, result);
                    }
                }
            }
        }
        return featureMap;
    }

    /**
     * 词性组成规则比率计算
     *
     * @param list 数据源Map<属性标识，数据>
     * @return Map<属性标识 ， Map < 词性 ， 词性占比>>
     */
    public Map<String, Map<String, Double>> getCombinationNaturesProbabiliMap(
            List<Map<String, String>> list) {
        double limit = 0.005;
        double minFeatureCount = list.size() / 10;
        Map<String, Integer> featureCountMap = new HashMap<>();
        Map<String, Map<String, Double>> featureMap = new HashMap<>();
        Map<String, Map<String, Double>> countMap = new HashMap<>();
        if (list != null && list.size() > 0) {
            for (Map<String, String> map : list) {
                for (String key : map.keySet()) {
                    if (!countMap.containsKey(key)) {
                        featureCountMap.put(key, 0);
                        countMap.put(key, new HashMap<String, Double>());
                    }
                    int featureCount = featureCountMap.get(key) + 1;
                    featureCountMap.put(key, featureCount);
                    String feature = StringUtils.join(
                            Analysis.getDicAnalysisNatures(map.get(key)), "#");
                    if (!countMap.get(key).containsKey(feature)) {
                        countMap.get(key).put(feature, 0d);
                    }
                    double count = countMap.get(key).get(feature) + 1;
                    countMap.get(key).put(feature, count);
                }
            }

            for (String attr : countMap.keySet()) {
                if (featureCountMap.get(attr) <= minFeatureCount) {
                    continue;
                }
                featureMap.put(attr, new HashMap<String, Double>());
                for (String key : countMap.get(attr).keySet()) {
                    double result = new BigDecimal(countMap.get(attr).get(key)
                            / list.size())
                            .setScale(4, BigDecimal.ROUND_HALF_UP)
                            .doubleValue();
                    if (result > limit) {
                        featureMap.get(attr).put(key, result);
                    }
                }
            }
        }
        return featureMap;
    }

    // 余弦相似度计算
    public static Double getCosineSimilarity(Map<String, Double> m1,
                                             Map<String, Double> m2) {
        double d = 0;
        Map<String, Double> tempM1 = new HashMap<>();
        Map<String, Double> tempM2 = new HashMap<>();
        int joinCount = 0;
        if (m1 == null || m2 == null) {
            return d;
        }

        for (String key : m1.keySet()) {
            tempM1.put(key, m1.get(key));
            if (m2.containsKey(key)) {
                joinCount++;
            } else {
                tempM2.put(key, 0d);
            }
        }
        if (joinCount <= 0) {
            return d;
        }
        for (String key : m2.keySet()) {
            tempM2.put(key, m2.get(key));
            if (!m1.containsKey(key)) {
                tempM1.put(key, 0d);
            }
        }
        double numerator = 0d;
        double denominator1 = 0d;
        double denominator2 = 0d;
        for (String key : tempM1.keySet()) {
            numerator += tempM1.get(key) * tempM2.get(key);
            denominator1 += Math.pow(tempM1.get(key), 2);
            denominator2 += Math.pow(tempM2.get(key), 2);
        }
        denominator1 = Math.pow(denominator1, 0.5);
        denominator2 = Math.pow(denominator2, 0.5);
        double denominator = denominator1 * denominator2;
        if (denominator != 0)
            d = numerator / denominator;
        return new BigDecimal(d).setScale(4, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}
