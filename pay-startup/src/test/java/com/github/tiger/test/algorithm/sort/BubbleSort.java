package com.github.tiger.test.algorithm.sort;

/**
 * 冒泡排序
 * <p>
 * 时间复杂度：O(n^2)（平方阶）
 * <p>
 * 空间复杂度：O(1)
 * <p>
 * 稳定性：稳定排序算法
 *
 * @author liuhongming
 * @date 07/06/2019
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = new int[]{7, 4, 9, 2, 6, 1};

        bubbleSort(arr);

        for (int n : arr) {
            System.out.println(n);
        }
    }

    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            /**
             * 优化：判断是否有序，如果有序就不用遍历了
             */
            boolean isSorted = true;
            /**
             * 相邻元素比较
             */
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    /**
                     * 相邻元素交换
                     */
                    swap(arr, j, j + 1);
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    public static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }

}
