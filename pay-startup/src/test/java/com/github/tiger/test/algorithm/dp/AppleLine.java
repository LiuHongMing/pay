package com.github.tiger.test.algorithm.dp;

/**
 * @author liuhongming
 *
 * 动态规划（中级）：
 *
 * 状态：S[i][j]，表示我们走到(i, j)这个格子时，最多能收集到多少个苹果。
 *
 * 状态转移方程：S[i][j]=A[i][j] + max(S[i-1][j], if i>0 ; S[i][j-1], if j>0)
 *
 * 其中i代表行，j代表列，下标均从0开始；A[i][j]代表格子(i, j)处的苹果数量。
 * S[i][j]有两种计算方式：
 * 1. 对于每一行，从左向右计算，然后从上到下逐行处理。
 * 2. 对于每一列，从上到下计算，然后从左向右逐列处理。
 * 这样做的目的是为了在计算S[i][j]时，S[i-1][j]和S[i][j-1]都已经计算出来了。
 */
public class AppleLine {

    public static void main(String[] args) {

        int[][] apples = {{7, 1, 9},{3, 0, 10},{1, 2, 5}};
        int row = apples.length;
        int col = apples[0].length;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(apples[i][j] + " ");
            }
            System.out.print("\n");
        }

        int n = 2;
        int m = 3;

        int amount = lookup(apples, n, m);

        System.out.println(amount);
    }

    public static int lookup(int[][] apples, int n, int m) {
        int[][] cells = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                /**
                 * 状态转移
                 */
                cells[i][j] = apples[i][j] + max(cells, i, j);
            }
        }

        return cells[n - 1][m - 1];
    }

    public static int max(int[][] cells, int i, int j) {
        int val = 0;

        if (i > 0 && j > 0) {
            val = Math.max(cells[i - 1][j], cells[i][j - 1]);
        } else if (i > 0 && j == 0) {
            val = cells[i - 1][j];
        } else if (i == 0 && j > 0) {
            val = cells[i][j - 1];
        }

        return val;
    }

}
