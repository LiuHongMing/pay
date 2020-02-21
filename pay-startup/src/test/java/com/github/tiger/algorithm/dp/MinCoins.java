package com.github.tiger.algorithm.dp;

/**
 *
 * 动态规划背后的基本思想非常简单。
 * 大致上，若要解一个给定问题，我们需要解其不同部分（即子问题），再根据子问题的解以得出原问题的解。
 *
 * 动态规划（入门）：
 *
 * 如果我们有面值为1元、3元和5元的硬币若干枚，如何用最少的硬币凑够11元？
 *
 * 状态：d(i) = j，i 表示金额，j表示硬币数
 *
 * 状态转移方程：d(i)=min{ d(i-Vj)+1 }，其中 i-j >= 0，Vj表示第j个硬币的面值
 *
 * 例如：
 *
 * d(0)=0
 *
 * d(1)=d(1-1)+1=d(0)+1=0+1=1
 *
 * d(2)=d(2-1)+1=d(1)+1=1+1=2
 *
 * d(3)=d(3-1)+1=d(2)+1=2+1=3
 *
 * d(3)=min{ d(3-1)+1, d(3-3)+1 }
 *
 * @author liuhongming
 *
 */
public class MinCoins {

    public static void main(String[] args) {

        /**
         * 计算金额
         */
        int money = 11;

        /**
         * 硬币面值
         */
        int[] values = new int[]{1, 3, 5};

        int minCoinAmount = minCoins(money, values);

        System.out.println(minCoinAmount);

    }

    public static int minCoins(int money, int[] values) {

        /**
         * 初始化
         */
        int[] minCoin = new int[money + 1];
        for (int i = 0; i < minCoin.length; i++) {
            minCoin[i] = Integer.MAX_VALUE;
        }
        minCoin[0] = 0;

        for (int i = 1; i <= money; i++) {
            for (int j = 0; j < values.length; j++) {
                int value = values[j];
                if (value <= i && minCoin[i - value] + 1 < minCoin[i]) {
                    minCoin[i] = minCoin[i - value] + 1;
                }
            }
        }

        return minCoin[money];
    }

}
