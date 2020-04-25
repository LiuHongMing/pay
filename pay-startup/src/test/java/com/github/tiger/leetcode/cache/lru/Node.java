package com.github.tiger.leetcode.cache.lru;

public class Node {

    int key, val;
    Node next, prev;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }

}
