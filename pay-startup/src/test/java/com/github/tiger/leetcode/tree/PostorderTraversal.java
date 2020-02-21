package com.github.tiger.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树后序遍历
 *
 * 给定一个二叉树，返回它的 后序 遍历。
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
 * 输出: [3,2,1]
 */
public class PostorderTraversal {

    public static void main(String[] args) {

        Integer[] array = {1, null, 2, 3};
        TreeNode root = new TreeNode(array[0]);
        root.right = new TreeNode(array[2]);
        root.right.left = new TreeNode(array[3]);

        List<Integer> result = postorderTraversal(root);
        for (Integer val : result) {
            System.out.printf("%s,", val);
        }
    }

    /**
     * 递归后序遍历
     *
     * @param root
     * @return
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>(16);
        if (root == null) {
            return result;
        }

        result.addAll(postorderTraversal(root.left));
        result.addAll(postorderTraversal(root.right));
        result.add(root.val);

        return result;
    }

}
