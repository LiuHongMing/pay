package com.github.tiger.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树中序遍历
 *
 * 给定一个二叉树，返回它的 中序 遍历。
 * <p>
 * 示例:
 * <p>
 * 输入: [1,null,2,3]
 * 1
 * \
 * 2
 * /
 * 3
 * <p>
 * 输出: [1,3,2]
 */
public class MiddleorderTraversal {

    public static void main(String[] args) {

        Integer[] array = {1, null, 2, 3};
        TreeNode root = new TreeNode(array[0]);
        root.right = new TreeNode(array[2]);
        root.right.left = new TreeNode(array[3]);

        List<Integer> result = middleorderTraversal(root);
        for (Integer val : result) {
            System.out.printf("%s,", val);
        }
    }

    /**
     * 递归中序遍历
     *
     * @param root
     * @return
     */
    public static List<Integer> middleorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>(16);
        if (root == null) {
            return result;
        }

        result.addAll(middleorderTraversal(root.left));
        result.add(root.val);
        result.addAll(middleorderTraversal(root.right));

        return result;
    }

}
