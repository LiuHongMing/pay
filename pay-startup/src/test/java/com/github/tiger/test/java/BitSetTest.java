package com.github.tiger.test.java;

import org.junit.Test;

import java.util.BitSet;

/**
 * 位集
 * 
 * @author jason.liuhongming
 * 
 */
public class BitSetTest {

	@Test
	public void testNumeric() {
		BitSet xnbs = new BitSet();
		xnbs.set(6);
		System.out.printf("xnBs:length()=%d,size()=%d\n", xnbs.length(),
				xnbs.size());
		xnbs.set(100);
		System.out.printf("xnBs:length()=%d,size()=%d\n", xnbs.length(),
				xnbs.size());

		BitSet ynbs = new BitSet();
		ynbs.set(8);
		System.out.printf("ynBs:length()=%d,size()=%d\n", ynbs.length(),
				xnbs.size());

		xnbs.or(ynbs);
		System.out.printf("xnBs:length()=%d,size()=%d\n", xnbs.length(),
				xnbs.size());

		for (int i = 0; i < xnbs.size(); i++) {
			System.out.printf("numeric(%d:This's %s)\n", i, xnbs.get(i));
		}
	}

}
