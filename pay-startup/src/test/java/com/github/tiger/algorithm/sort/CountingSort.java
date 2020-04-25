package com.github.tiger.algorithm.sort;

/**
 * 计数排序（非比较排序）
 * 时间复杂度：O(n + m)（线性阶）
 * <p>
 * 最好：O(n + m)
 * 最坏：O(n + m)
 * <p>
 * 空间复杂度：O(n)
 * <p>
 * 稳定性：稳定
 *
 * @author liuhongming
 * @date 2020-03-17
 */
public class CountingSort {

    public static void main(String[] args) {
        int[] arr = new int[]{8, 2, 5, 9, 7, 3};

        countingSort(arr);

        for (int n : arr) {
            System.out.println(n);
        }
    }

    public static void countingSort(int[] arr) {

        // 找出数组中的最大值
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        // 初始化计数数组
        int[] countArr = new int[max + 1];

        // 计数
        for (int i = 0; i < arr.length; i++) {
            countArr[arr[i]]++;
            arr[i] = 0;
        }

        // 排序
        int index = 0;
        for (int i = 0; i < countArr.length; i++) {
            if (countArr[i] > 0) {
                arr[index++] = i;
            }
        }
    }

}
