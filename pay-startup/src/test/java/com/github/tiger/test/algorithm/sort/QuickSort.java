package com.github.tiger.test.algorithm.sort;

/**
 * 快速排序
 *
 * 时间复杂度：O（log(2)n）（对数阶）
 *
 * @author liuhongming
 * @date 07/06/2019
 */
public class QuickSort {

    public void quickSort(int[] arr, int start, int end) {
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

}
