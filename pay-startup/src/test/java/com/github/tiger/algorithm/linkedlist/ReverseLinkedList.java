package com.github.tiger.algorithm.linkedlist;

/**
 * 反转链表
 *
 * @author liuhongming
 */
public class ReverseLinkedList {

    private static class Node {

        int value;

        Node nextNode;

        Node(int value) {
            this.value = value;
        }

        static Node build(int value) {
            return new Node(value);
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    /**
     * 递归
     *
     * @param head
     * @return
     */
    public static Node reverse(Node head) {
        if (head == null || head.getNextNode() == null) {
            return head;
        }
        Node next = reverse(head.getNextNode());
        head.getNextNode().setNextNode(head);
        head.setNextNode(null);

        return next;
    }

    /**
     * 非递归
     *
     * @param head
     * @return
     */
    public static Node reverse2(Node head) {

        if (head == null) {
            return head;
        }

        Node pre = head;
        Node curr = head.getNextNode();
        Node next;
        while (null != curr) {
            next = curr.getNextNode();
            curr.setNextNode(pre);
            pre = curr;
            curr = next;
        }
        head.setNextNode(null);
        head = pre;

        return head;
    }

    public static void main(String[] args) {
        Node head = Node.build(97);
        Node curr = null;
        Node tmp;
        for (int i = 1; i < 10; i++) {
            tmp = Node.build(i + 97);
            if (1 == i) {
                head.setNextNode(tmp);
            } else {
                curr.setNextNode(tmp);
            }
            curr = tmp;
        }

        Node tail = head;
        do {
            System.out.print((char) tail.getValue());
            tail = tail.getNextNode();
        } while (tail != null);
        System.out.print(",");
        tail = ReverseLinkedList.reverse(head);
        do {
            System.out.print((char) tail.getValue());
            tail = tail.getNextNode();
        } while (tail != null);
        tail = ReverseLinkedList.reverse(head);

    }

}
