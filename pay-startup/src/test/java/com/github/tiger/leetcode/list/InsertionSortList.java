package com.github.tiger.leetcode.list;

/**
 * 插入排序算法：
 * 1. 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 * 2. 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 * 3. 重复直到所有输入数据插入完为止。
 */
public class InsertionSortList {

    public static void main(String[] args) {
        int[] arr = {-1, 5, 3, 4, 0};
        ListNode first = null, last = null;
        for (int val : arr) {
            ListNode l = last;
            ListNode newNode = new ListNode(val);
            last = newNode;
            if (l == null) {
                first = newNode;
            } else {
                l.next = newNode;
            }
        }

        insertionSortList(first);

        ListNode curr = first;
        while (curr != null) {
            System.out.print(curr.val + ",");
            curr = curr.next;
        }
    }

    public static ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);

        while (head != null) {
            ListNode cur = dummy;
            ListNode next = head.next;
            // 寻找节点插入位置
            while (cur.next != null && cur.next.val < head.val) {
                cur = cur.next;
            }
            // 交换节点
            head.next = cur.next;
            cur.next = head;
            head = next;
        }
        return dummy.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


}
