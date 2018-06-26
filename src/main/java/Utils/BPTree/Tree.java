package Utils.BPTree;

import Utils.Constants;

public class Tree implements Constants {

	private Node root;

	public Tree(){
		root = new LeafNode();
	}

	public LeafNode search(String key, Node node) {
		if (!(node instanceof LeafNode)) {
			int i = node.size;
			do {
				--i;
			} while (key.compareTo(node.keys[i]) < 0);
			++i;
			return search(key, ((InternalNode) node).children[i]);
		} else {
			return (LeafNode) node;
		}
	}

	public void add(String key, long value) {
		if (!root.hasSpace()) {
			InternalNode newRoot = new InternalNode();
			LeafNode right = new LeafNode();
			LeafNode left = (LeafNode) root;    // might cause null pointer
			right.parent = left.parent = newRoot;
			left.next = right;

			int mid = (MAX_SIZE % 2 == 0) ? (MAX_SIZE / 2) : (MAX_SIZE - 1) / 2;

			for (int i = mid; i < MAX_SIZE; ++i) {
				left.keys[i] = right.keys[i - mid];
				left.pointers[i] = right.pointers[i - mid];
			}

			left.size = mid;
			right.size = (MAX_SIZE % 2 == 0) ? (mid) : (mid + 1);

			root = newRoot;    // might cause null pointer
		}

		LeafNode target = search(key, root);

		if (target.hasSpace()) {
			int index = target.size;
			while (key.compareTo(target.keys[index - 1]) < 0) {
				target.keys[index] = target.keys[index - 1];
				target.pointers[index] = target.pointers[index - 1];
				--index;
			}
			target.keys[index] = key;
			target.pointers[index] = value;
		} else {
			split(target);
			add(key, value);
		}
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
				left.keys[i] = right.keys[i - mid];
				left.pointers[i] = right.pointers[i - mid];
			}

			left.size = mid;
			right.size = (MAX_SIZE % 2 == 0) ? (mid) : (mid + 1);
		} else {
			InternalNode left = (InternalNode) node;
			InternalNode right = new InternalNode();
			right.parent = left.parent;
			for (int i = mid; i < MAX_SIZE; ++i) {
				left.keys[i] = right.keys[i - mid];
				left.children[i] = right.children[i - mid];
			}

			left.size = mid;
			right.size = (MAX_SIZE % 2 == 0) ? (mid) : (mid + 1);
		}

	}

}
