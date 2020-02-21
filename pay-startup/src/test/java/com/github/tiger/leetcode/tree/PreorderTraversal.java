package com.github.tiger.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树前序遍历
 * <p>
 * 给定一个二叉树，返回它的 前序 遍历。
 * <p>
 *  示例:
 * <p>
 * 输入: [1,null,2,3]
 * 1
 * \
 * 2
 * /
 * 3
 * <p>
 * 输出: [1,2,3]
 */
public class PreorderTraversal {

    public static void main(String[] args) {

        Integer[] array = {1, null, 2, 3};
        TreeNode root = new TreeNode(array[0]);
        root.right = new TreeNode(array[2]);
        root.right.left = new TreeNode(array[3]);

        List<Integer> result = preorderTraversal(root);
        for (Integer val : result) {
            System.out.printf("%s,", val);
        }
    }

    /**
     * 递归前序遍历
     *
     * @param root
     * @return
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>(16);
        if (root == null) {
            return result;
        }

        result.add(root.val);
        result.addAll(preorderTraversal(root.left));
        result.addAll(preorderTraversal(root.right));

        return result;
    }

}
