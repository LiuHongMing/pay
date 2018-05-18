package com.github.tiger.test.trie;

/**
 * Trie 单词查找树
 *
 * @author liuhongming
 */
public interface Trie {

    /**
     * Check if the trie contains the word.
     *
     * @param word
     * @return
     */
    boolean contains(String word);

    /**
     * Get a number of occurrences of 'word' in this trie.
     *
     * @param word
     * @return
     */
    int frequence(String word);

    /**
     * Inserts 'word' into this trie.
     *
     * @param word
     */
    int insert(String word);

    /**
     * Remove 'word' from this trie.
     *
     * @param word
     * @return
     */
    boolean remove(String word);

    /**
     * Get the number of unique word in this trie.
     *
     * @return
     */
    int size();
}
