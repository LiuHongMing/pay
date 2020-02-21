package com.github.tiger.leetcode.cache.example;

import java.util.HashMap;

/**
 * LRU (最近最少使用) 缓存机制
 * <p>
 * 哈希双链表实现
 */
public class LRUCache {

    private int capacity;

    private HashMap<Integer, Node> map;
    private DoubleList cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>(capacity);
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
                Node last = cache.removeLast();
                map.remove(last.key);
            }
            // 直接插入到链表头部
            cache.addFirst(x);
            map.put(key, x);
        }
    }

}
