package com.github.tiger.test.algorithm.sort;

/**
 * 快速排序
 *
 * @author liuhongming
 */
public class QuickSort {

    public void quickSort(int[] a, int start, int end) {
        int i, j;
        i = start;
        j = end;

        while (i < j) {
            while (i < j && a[i] <= a[j])
                j--;
            if (i < j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
            while (i < j && a[i] < a[j])
                i++;
            if (i < j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }

        }

        if (i - start > 1)
            quickSort(a, 0, i - 1);

        if (end - j > 1)
            quickSort(a, j + 1, end);
    }

}
