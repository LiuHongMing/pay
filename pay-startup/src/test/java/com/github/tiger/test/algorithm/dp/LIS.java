package com.github.tiger.test.algorithm.dp;

/**
 * @author liuhongming
 *
 * 动态规划（初级）：最长递增子序列（LIS：longest increasing subsequence）
 *
 * 一个序列有N个数：A[1],A[2],…,A[N]，求出最长递增子序列的长度
 *
 * 状态：d(i) = j，i 表示序列号，j 表示递增序列长度
 *
 * 状态转移公式：d(i) = max{ 1, d(j)+1 }，其中 j<i，A[j]<=A[i]
 *
 * 用大白话解释就是，想要求d(i)，就*把i前面的各个子序列*中，最后一个数不大于A[i]的序列长度加1，然后取出最大的长度即为d(i)。
 * 当然了，有可能i前面的各个子序列中最后一个数都大于A[i]，那么d(i)=1， 即它自身成为一个长度为1的子序列。
 *
 */
public class LIS {

    public static void main(String[] args) {
        int[] a = new int[]{5, 3, 4, 8, 9, 9, 6};
        int n = a.length;

        int len = lis(a, n);
        System.out.println(len);
    }

    /**
     * 时间复杂度 O(n^2)
     */
    public static int lis(int[] a, int n) {

        int len = 1;

        /**
         * 状态
         */
        int[] d = new int[n];

        for (int i = 0; i < n; i++) {
            /**
             * 初始值
             */
            d[i] = 1;
            for (int j = 0; j < i; j++) {
                /**
                 * 状态转移
                 */
                if (a[j] <= a[i] && d[j] + 1 > d[i]) {
                    d[i] = d[j] + 1;
                }
            }
            if (d[i] > len) {
                len = d[i];
            }
        }

        return len;
    }

}
