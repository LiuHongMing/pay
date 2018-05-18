package com.github.tiger.test.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuhongming
 */
public class TrieImpl implements Trie {

    final TrieNode root;

    int size;

    boolean caseSensitive;

    public TrieImpl(boolean caseSensitive) {
        this.root = new TrieNode((char) 0);
        this.size = 0;
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean contains(String word) {
        if (word == null) {
            return false;
        }

        TrieNode tn = root.lookup(caseSensitive ? word :
                word.toLowerCase(), 0);

        return tn != null;
    }

    @Override
    public int frequence(String word) {
        if (word == null) {
            return 0;
        }

        TrieNode tn = root.lookup(caseSensitive ? word :
                word.toLowerCase(), 0);

        return tn == null ? 0 : tn.frequence;
    }

    @Override
    public int insert(String word) {
        if (word == null)
            return 0;

        int i = root.insert(caseSensitive ? word : word.toLowerCase(), 0);

        if (i == 1) {
            size++;
        }

        return i;
    }

    @Override
    public boolean remove(String word) {
        if (word == null) {
            return false;
        }

        if (root.remove(caseSensitive ? word :
                word.toLowerCase(), 0)) {
            size--;
            return true;
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return "TrieImpl{" +
                "root=" + root +
                '}';
    }

    private class TrieNode {

        char c;

        int frequence;

        Map<Character, TrieNode> children;

        public TrieNode(char c) {
            this.c = c;
            this.frequence = 0;
            this.children = null;
        }

        int insert(String s, int pos) {
            if (s == null || pos >= s.length()) {
                return 0;
            }

            if (children == null) {
                children = new HashMap<>();
            }

            char ch = s.charAt(pos);
            TrieNode tn = children.get(ch);
            if (tn == null) {
                tn = new TrieNode(ch);
                children.put(ch, tn);
            }

            if (pos == s.length() - 1) {
                tn.frequence++;
                return tn.frequence;
            } else {
                return tn.insert(s, pos + 1);
            }
        }

        boolean remove(String s, int pos) {
            if (children == null || s == null) {
                return false;
            }

            char ch = s.charAt(pos);
            TrieNode tn = children.get(ch);

            if (tn == null) {
                return false;
            }

            boolean ret;
            if (pos == s.length() - 1) {
                int before = tn.frequence;
                tn.frequence = 0;
                ret = before > 0;
                return ret;
            } else {
                ret = tn.remove(s, pos + 1);
            }

            if (tn.children == null || tn.frequence == 0) {
                children.remove(tn.c);
                if (children.size() == 0) {
                    children = null;
                }
            }

            return ret;
        }

        TrieNode lookup(String s, int pos) {
            if (s == null) {
                return null;
            }

            if (pos >= s.length() || children == null) {
                return null;
            } else if (pos == s.length() - 1) {
                return children.get(s.charAt(pos));
            } else {
                TrieNode tn = children.get(s.charAt(pos));
                return tn == null ? null : children.get(s.charAt(pos));
            }
        }

        void dump(StringBuilder sb, String prefix) {
            sb.append(prefix).append(c)
                    .append("(").append(frequence).append(")")
                    .append("\n");

            if (children == null) {
                return;
            }

            for (TrieNode tn : children.values()) {
                tn.dump(sb, prefix + (c == 0 ? "" : c));
            }
        }
    }
}
