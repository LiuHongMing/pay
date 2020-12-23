package com.github.tiger.leetcode;

/**
 * @className: MaxArea
 * @description: 盛最多水的容器
 * @author liuhongming
 * @date 2020/09/08
 */
public class MaxArea {

    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1, res = 0;
        while (i < j) {
            int heigth_ = Math.min(height[i], height[j]);
            res = Math.max(res, heigth_ * (j - i));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }

        }
        return res;
    }

}
