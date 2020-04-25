package com.github.tiger.algorithm.sort;

/**
 * 归并排序（比较排序）
 * <p>
 * 时间复杂度：O(nlogn)（线性对数阶）
 * <p>
 * 最好：O(nlogn)
 * 最坏：O(nlogn)
 * <p>
 * 空间复杂度：O(n)
 * <p>
 * 稳定性：稳定
 *
 * @author liuhongming
 * @date 2019-07-30
 */
public class MergeSort {

    public static void mergeSort(int[] arr) {
        int[] tempArr = new int[arr.length];
        mergeSort(arr, tempArr, 0, arr.length - 1);
    }

    public static void mergeSort(int[] arr, int[] tempArr, int startIdx, int endIdx) {
        if (endIdx <= startIdx) {
            return;
        }
        // 中部下标
        int middleIdx = startIdx + (endIdx - startIdx) / 2;

        // 分解
        mergeSort(arr, tempArr, startIdx, middleIdx);
        mergeSort(arr, tempArr, middleIdx + 1, endIdx);

        // 归并
        merge(arr, tempArr, startIdx, middleIdx, endIdx);
    }

    /**
     * 归并排序
     *
     * @param arr       排序数组
     * @param tempArr   临时存储数组
     * @param startIdx
     * @param middleIdx
     * @param endIdx
     */
    public static void merge(int[] arr, int[] tempArr,
                             int startIdx, int middleIdx, int endIdx) {
        for (int s = startIdx; s <= endIdx; s++) {
            tempArr[s] = arr[s];
        }

        // 左边首位下标
        int left = startIdx;
        // 右边首位下标
        int right = middleIdx + 1;

        for (int k = startIdx; k <= endIdx; k++) {
            if (left > middleIdx) {
                // 如果左边的首位下标大于中部下标，证明左边的数据已经排完了。
                arr[k] = tempArr[right++];
            } else if (right > endIdx) {
                // 如果右边的首位下标大于数组长度，证明右边的数据已经排完了。
                arr[k] = tempArr[left++];
            } else if (tempArr[right] > tempArr[left]) {
                arr[k] = tempArr[right++];
            } else {
                arr[k] = tempArr[left++];
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 4, 9, 2, 6, 1};

        mergeSort(arr);

        for (int n : arr) {
            System.out.println(n);
        }
    }
}
