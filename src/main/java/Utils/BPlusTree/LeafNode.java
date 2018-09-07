package Utils.BPlusTree;

import Objects.Source;

import static java.util.Arrays.fill;

/**
 * Class documentation goes here
 */
class LeafNode extends Node {
	LeafNode next;
	Source[] pointers;

	LeafNode() {
		super();
		pointers = new Source[keys.length];
		fill(pointers, null);
	}

}
