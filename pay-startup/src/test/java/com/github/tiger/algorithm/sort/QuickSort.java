package com.github.tiger.algorithm.sort;

/**
 * 快速排序（比较排序）
 * <p>
 * 时间复杂度：O(nlogn)（线性对数阶）
 * <p>
 * 最好：O(nlogn)
 * 最坏：O(n^2)
 * <p>
 * 空间复杂度：O(logn)
 * <p>
 * 稳定性：不稳定
 *
 * @author liuhongming
 * @date 2019-08-06
 */
public class QuickSort {

    public static void quickSort(int[] arr, int start, int end) {
        int i, j;
        i = start;
        j = end;

        while (i < j) {
            while (i < j && arr[i] <= arr[j])
                j--;
            if (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
            while (i < j && arr[i] < arr[j])
                i++;
            if (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }

        }

        if (i - start > 1)
            quickSort(arr, 0, i - 1);

        if (end - j > 1)
            quickSort(arr, j + 1, end);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 4, 9, 2, 6, 1};

        quickSort(arr, 0, arr.length - 1);

        for (int n : arr) {
            System.out.println(n);
        }
    }

}
