package com.github.tiger.leetcode.list;

/**
 * 重排链表
 *
 * 给定一个单链表 L：L0→L1→…→Ln-1→Ln，
 * <p>
 * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
 * <p>
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 * <p>
 * 示例 1:
 * <p>
 * 给定链表 1->2->3->4, 重新排列为 1->4->2->3.
 * 示例 2:
 * <p>
 * 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
 */
public class ReorderList {

    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        ListNode _head = head;
        for (; ; ) {
            ListNode tail = _head;
            ListNode cursor = tail;
            for (; ; ) {
                ListNode cursorNext = cursor.next;
                if (cursorNext != null) {
                    tail = cursor;
                    cursor = cursorNext;
                } else {
                    tail.next = null;
                    cursor.next = _head.next;
                    _head.next = cursor;
                    _head = cursor.next;
                    break;
                }
            }
            if (_head == null
                    || _head.next == null) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode tail = head;
        for (int i = 2; i <= 5; i++) {
            ListNode node = new ListNode(i);
            tail.next = node;
            tail = tail.next;
        }

        reorderList(head);

        ListNode _head = head;
        for (; ; ) {
            System.out.printf("%s,", _head.val);
            _head = _head.next;
            if (_head == null) {
                break;
            }
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
