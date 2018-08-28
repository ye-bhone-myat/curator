package Utils.BPlusTree;

import Utils.Constants;

public class Tree implements Constants {

	private Node root;
	private int size;

	public Tree() {
		root = new LeafNode();
		size = 0;
	}

	public long search(String key) {
		LeafNode node = tree_search(key, root);
		if (node.isEmpty()) return -1;
		for (int i = 0; i < MAX_SIZE; ++i) {
			if (key.equals(node.keys[i])) {
				return node.pointers[i];
			}
		}
		return -1;
	}

	private LeafNode tree_search(String key, Node node) {
		if (!(node instanceof LeafNode)) {
			int i = node.size - 1;
			while (i >= 0 && key.compareTo(node.keys[i]) < 0) {
				--i;
			}
			++i;
			return tree_search(key, ((InternalNode) node).children[i]);
		} else {
			return (LeafNode) node;
		}
	}

	public void add(String key, long value) {
		if (!root.hasSpace()) {

//			InternalNode newRoot = new InternalNode();
//			LeafNode right = new LeafNode();
//			LeafNode left = (LeafNode) root;
//
//			int mid = (MAX_SIZE % 2 == 0) ? (MAX_SIZE / 2) : (MAX_SIZE - 1) / 2;
//
//			for (int i = mid; i < MAX_SIZE; ++i) {
//				right.keys[i - mid] = left.keys[i];
//				right.pointers[i - mid] = left.pointers[i];
//				left.keys[i] = null;
//				left.pointers[i] = -1;
//			}
//
//			left.size = mid;
//			right.size = (MAX_SIZE % 2 == 0) ? (mid) : (mid + 1);
//
//			right.parent = left.parent = newRoot;
//			left.next = right;
//			newRoot.keys[0] = right.keys[0];
//			newRoot.children[0] = left;
//			newRoot.children[1] = right;
//			newRoot.size += 1;
//
//			root = newRoot;
		}

		LeafNode target = tree_search(key, root);

		if (target.hasSpace()) {
			int index = target.size;
			while (index > 0 && key.compareTo(target.keys[index - 1]) < 0) {
				target.keys[index] = target.keys[index - 1];
				target.pointers[index] = target.pointers[index - 1];
				--index;
			}
			target.keys[index] = key;
			target.pointers[index] = value;
			target.size += 1;
			++size;
		} else {
			split(target);
			add(key, value);
		}
	}

	public int getSize() {
		return size;
	}

	/**
	 * @param node Node to be split
	 */
	// might not need a return
	private void split(Node node) {

		if (!node.parent.hasSpace()) {
			split(node.parent);
		}

		// calculate midpoint
		int mid = (MAX_SIZE % 2 == 0) ? (MAX_SIZE / 2) : (MAX_SIZE - 1) / 2;

		if (node instanceof LeafNode) {
			LeafNode left = (LeafNode) node;
			LeafNode right = new LeafNode();
			right.parent = left.parent;
			right.next = left.next;
			left.next = right;
			for (int i = mid; i < MAX_SIZE; ++i) {
				right.keys[i - mid] = left.keys[i];
				right.pointers[i - mid] = left.pointers[i];
				left.keys[i] = null;
				left.pointers[i] = -1;
			}

			left.size = mid;
			right.size = (MAX_SIZE % 2 == 0) ? (mid) : (mid + 1);

			int index = left.parent.size;
			while (index > 0 && right.keys[0].compareTo(left.parent.keys[index - 1]) < 0) {
				left.parent.keys[index] = left.parent.keys[index - 1];
				left.parent.children[index + 1] = left.parent.children[index];
				--index;
			}
			left.parent.keys[index] = right.keys[0];
			left.parent.children[index + 1] = right;
			left.parent.size += 1;

		} else {
//			InternalNode left = (InternalNode) node;
//			InternalNode right = new InternalNode();
//			right.parent = left.parent;
//			for (int i = mid; i < MAX_SIZE; ++i) {
//				right.keys[i - mid] = left.keys[i];
//				right.children[i - mid] = left.children[i];
//				left.keys[i] = null;
//				left.children[i] = null;
//			}
//
//			left.size = mid;
//			right.size = (MAX_SIZE % 2 == 0) ? (mid) : (mid + 1);
//
//			int index = left.parent.size;
//			while (index > 0 && right.keys[0].compareTo(left.parent.keys[index - 1]) < 0) {
//				left.parent.keys[index] = left.parent.keys[index - 1];
//				left.parent.children[index + 1] = left.parent.children[index];
//				--index;
//			}
//			left.parent.keys[index] = right.keys[0];
//			left.parent.children[index + 1] = right;
//			left.parent.size += 1;

		}

	}

}
