package com.github.tiger.algorithm.sort;

import java.util.ArrayList;

/**
 * 基数排序（非比较排序）
 * 时间复杂度：O(n * m)（线性阶）
 * <p>
 * 最好：O(n * m)
 * 最坏：O(n * m)
 * <p>
 * 空间复杂度：O(n + m)
 * <p>
 * 稳定性：稳定
 *
 * @author liuhongming
 * @date 2020-03-20
 */
public class RadixSort {

    public static void main(String[] args) {
        int[] arr = new int[]{7, 4, 9, 2, 6, 1, 33};

        radixSort(arr);

        for (int n : arr) {
            System.out.println(n);
        }
    }

    public static void radixSort(int[] arr) {
        int length = arr.length;

        // 最大值
        int max = arr[0];
        for (int i = 0; i < length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // 最大数的位数
        int maxDigit = 0;
        while (max != 0) {
            max /= 10;
            maxDigit++;
        }

        // 桶列表
        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<>();

        // 长度为10 装入余数0-9的数据
        for (int i = 0; i < 10; i++) {
            bucketList.add(new ArrayList());
        }

        int mod = 10, div = 1;
        for (int i = 0; i < maxDigit; i++, div *= 10) {

            // 数据入桶
            for (int j = 0; j < length; j++) {
                // 计算余数 放入相应的桶
                int number = ((arr[j] / div) % mod);
                bucketList.get(number).add(arr[j]);
            }

            // 写回数组
            int index = 0;
            for (int j = 0; j < bucketList.size(); j++) {
                int size = bucketList.get(j).size();
                for (int k = 0; k < size; k++) {
                    arr[index++] = bucketList.get(j).get(k);
                }
                bucketList.get(j).clear();
            }
        }
    }

}
