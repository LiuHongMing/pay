package com.github.tiger.test.algorithm.sort;

/**
 * 插入排序
 * <p>
 * 时间复杂度：O(n^2)（平方阶）
 * <p>
 * 最好：O(n)
 * 最坏：O(n^2)
 * <p>
 * 空间复杂度：O(1)
 * <p>
 * 稳定性：稳定排序算法
 *
 * @author liuhongming
 * @date 07/08/2019
 */
public class InsertionSort {

    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            /**
             * 插入的数据
             */
            int value = arr[i];
            /**
             * 插入的位置
             */
            int j;
            /**
             * 排序区域内的元素比较
             */
            for (j = i - 1; j >= 0; j--) {
                if (arr[j] > value) {
                    /**
                     * 移动数据
                     */
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            /**
             * 插入数据
             */
            arr[j + 1] = value;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 4, 9, 2, 6, 1};

        insertionSort(arr);

        for (int n : arr) {
            System.out.println(n);
        }
    }
}
