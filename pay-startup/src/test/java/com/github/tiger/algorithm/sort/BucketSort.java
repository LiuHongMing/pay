package com.github.tiger.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序（非比较排序）
 * 时间复杂度：O(n + m)（线性阶）
 * <p>
 * 最好：O(n + m)
 * 最坏：O(n^2)
 * <p>
 * 空间复杂度：O(n + m)
 * <p>
 * 稳定性：稳定
 *
 * @author liuhongming
 * @date 2020-03-20
 */
public class BucketSort {

    public static void main(String[] args) {
        int[] arr = new int[]{7, 4, 9, 2, 6, 1};

        bucketSort(arr);

        for (int n : arr) {
            System.out.println(n);
        }
    }

    public static void bucketSort(int[] arr) {
        int length = arr.length;

        int max = arr[0];
        int min = arr[0];
        for (int i = 0; i < length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            } else if (arr[i] < min) {
                min = arr[i];
            }
        }

        // 差值
        int diff = max - min;

        List<List<Integer>> bucketList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            bucketList.add(new ArrayList<>());
        }

        // 每个桶的存数区间
        float section = (float) diff / (float) (length - 1);

        //数据入桶
        for (int i = 0; i < length; i++) {
            // 当前数除以区间得出存放桶的位置 减1后得出桶的下标
            int num = (int) (arr[i] / section) - 1;
            if (num < 0) {
                num = 0;
            }
            bucketList.get(num).add(arr[i]);
        }

        // 桶内排序
        for (int i = 0; i < bucketList.size(); i++) {
            Collections.sort(bucketList.get(i));
        }

        // 写入原数组
        int index = 0;
        for (List<Integer> list : bucketList) {
            for (int value : list) {
                arr[index] = value;
                index++;
            }
        }
    }

}
