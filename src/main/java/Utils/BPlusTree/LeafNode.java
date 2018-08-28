package Utils.BPlusTree;

import static java.util.Arrays.fill;

/**
 * Class documentation goes here
 */
class LeafNode extends Node {
	LeafNode next;
	long[] pointers;

	LeafNode() {
		super();
		pointers = new long[keys.length];
		fill(pointers, -1);
	}
}
