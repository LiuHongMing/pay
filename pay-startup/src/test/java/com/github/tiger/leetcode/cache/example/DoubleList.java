package com.github.tiger.leetcode.cache.example;

public class DoubleList {

    private Node head, tail; // 头尾虚节点

    private int size; // 链表元素数

    public DoubleList() {
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        this.head = tail;
        this.tail = head;
        this.size = 0;
    }

    // 在链表头部添加节点 x
    public void addFirst(Node x) {
        x.next = head.next;
        x.prev = head;
        head.next.prev = x;
        head.next = x;
        size++;
    }

    // 删除链表中 x 节点（x 一定存在）
    public void remove(Node x) {
        x.prev.next = x.next;
        x.next = x.prev;
        size--;
    }

    // 删除链表最后一个节点，返回节点
    public Node removeLast() {
        if (tail.prev == head) {
            return null;
        }

        Node last = tail.prev;
        remove(last);

        return last;
    }

    public int size() {
        return size;
    }
}
