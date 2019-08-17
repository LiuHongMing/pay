package com.github.tiger.test.algorithm.sort;

/**
 * 选择排序
 *
 * 时间复杂度：O(n^2)（平方阶）
 *
 * 空间复杂度：O(1)
 *
 * 稳定性：稳定排序算法
 *
 * @author liuhongming
 * @date 07/06/2019
 */
public class SelectionSort {

    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            /**
             * 最小值下标
             */
            int min = i;
            for (int j = i + 1; j < n; j++) {
                /**
                 * 查找最小元素
                 */
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            /**
             * 与最小元素交换
             */
            swap(arr, i, min);
        }
    }

    public static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }


    public static void main(String[] args) {
        int[] arr = new int[]{7, 4, 9, 2, 6, 1};

        selectionSort(arr);

        for (int n : arr) {
            System.out.println(n);
        }
    }
}
