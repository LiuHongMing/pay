package com.github.tiger.leetcode.cache;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * LRU (最近最少使用) 缓存机制
 */
public class LRUCache {

    private int capacity;
    private HashMap<Integer, Node> map;
    private LinkedList<Node> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
        this.cache = new LinkedList();
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));       // 返回  1
        cache.put(3, 3);    // 该操作会使得密钥 2 作废
        System.out.println(cache.get(2));       // 返回 -1 (未找到)
        cache.put(4, 4);    // 该操作会使得密钥 1 作废
        System.out.println(cache.get(1));       // 返回 -1 (未找到)
        System.out.println(cache.get(3));       // 返回  3
        System.out.println(cache.get(4));       // 返回  4
    }

    public int get(int key) {

        if (!map.containsKey(key)) {
            return -1;
        }

        int val = map.get(key).val;
        // 利用 put 方法把该数据提前
        put(key, val);

        return val;
    }

    public void put(int key, int value) {

        Node x = new Node(key, value);

        if (map.containsKey(key)) {
            // 删除旧的节点，新的插到头部
            cache.remove(map.get(key));
            cache.addFirst(x);
            // 更新 map 中对应的数据
            map.put(key, x);
        } else {
            if (cache.size() == capacity) {
                // 删除链表最后一个数据
                Node last = cache.getLast();
                cache.removeLast();
                map.remove(last.key);
            }
            // 直接插入到链表头部
            cache.addFirst(x);
            map.put(key, x);
        }
    }

    public class Node {

        int key, val;
        Node next, prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }

    }

}
