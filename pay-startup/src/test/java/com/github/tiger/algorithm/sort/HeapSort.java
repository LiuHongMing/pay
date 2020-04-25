package com.github.tiger.algorithm.sort;

/**
 * 堆排序（比较排序）
 * <p>
 * 时间复杂度：O(nlogn)（线性对数阶）
 * <p>
 * 最好：O(nlogn)
 * 最坏：O(nlogn)
 * <p>
 * 空间复杂度：O(1)
 * <p>
 * 稳定性：不稳定
 *
 * @author liuhongming
 * @date 2020-03-17
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = new int[]{8, 2, 5, 9, 7, 3};

        heapSort(arr);

        for (int n : arr) {
            System.out.println(n);
        }
    }

    public static void heapSort(int[] arr) {
        int length = arr.length;

        // 构建最大堆
        buildMaxHeap(arr, length);

        while (length > 0) {
            swap(arr, 0, length - 1);
            length--;
            adjustHeap(arr, 0, length);
        }

    }

    public static void buildMaxHeap(int[] arr, int length) {
        for (int i = length / 2; i >= 0; i--) {
            adjustHeap(arr, i, length);
        }
    }

    public static void adjustHeap(int[] arr, int index, int length) {
        // 当前要调整的节点下标
        int currentIndex = index;
        // 左节点下标
        int leftChild = index * 2;
        // 右节点下标
        int rightChild = index * 2 + 1;

        if (leftChild < length && arr[leftChild] > arr[currentIndex]) {
            currentIndex = leftChild;
        }

        if (rightChild < length && arr[rightChild] > arr[currentIndex]) {
            currentIndex = rightChild;
        }

        if (currentIndex != index) {
            swap(arr, currentIndex, index);
            adjustHeap(arr, currentIndex, length);
        }
    }

    public static void swap(int[] arr, int x, int y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
    }
}
