package com.github.tiger.leetcode;

/**
 * @className: RemoveDuplicates
 * @description: 删除排序数组中的重复项
 * @author liuhongming
 * @date 2020/09/08
 * @version 
 */
public class RemoveDuplicates {
    public int removeDuplicates(int[] nums) {
        int i = 0, j = 1;
        for (; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
                continue;
            }
        }
        return i + 1;
    }
}
