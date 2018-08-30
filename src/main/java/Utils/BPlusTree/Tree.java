package Utils.BPlusTree;


import Utils.Constants;

public class Tree implements Constants {
	private Node root;
	private int size;

	public Tree() {
		root = new LeafNode();
		size = 0;
	}

	/**
	 * Returns the long offset value associated with the <code>key</code>. If no such key is stored
	 * in the tree, a value of <code>-1</code> is returned.
	 * @param key the key to search for
	 * @return the offset value paired with the <code>key</code> as a <code>long</code>, or <code>-1</code>
	 */
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

	/**
	 * Searches the tree starting at <code>node</code>, for a <code>LeafNode</code> object. If the tree already
	 * contains <code>key</code>, this method returns the <code>LeafNode</code> object that contains said
	 * <code>key</code>. Otherwise, this method returns the <code>LeafNode</code> object to which <code>key</code>
	 * would belong to, should <code>key</code> be added.
	 * @param key the key to search for
	 * @param node the <code>Node</code> object to start the search from
	 * @return the <code>LeafNode</code> that <code>key</code> belongs to, or should belong to
	 */
	private LeafNode tree_search(String key, Node node) {
		if (node instanceof InternalNode) {
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

	public int getSize() {
		return size;
	}

	/**
	 * Adds an offset <code>value</code> to the tree using the <code>key</code>
	 * @param key the key to be stored with the offset value
	 * @param value the offset value to be stored
	 */
	public void add(String key, long value) {
		LeafNode target = tree_search(key, root);
		if (target.hasSpace()) {
			//add
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
			//split
			split(target);
			// call self
			add(key, value);
		}
	}

	/**
	 * Splits the given <code>node</code>, and grows the tree from the root where necessary.<br>
	 * The way the <code>keys</code> and <code>children</code> and/or <code>pointers</code> are separated between
	 * the split <code>Node</code> objects is determined by <code>BRANCHING_FACTOR</code> in {@link Constants}
	 * <i>*For future reference, the size of a <code>Node</code> object is the size of
	 * its <code>keys</code> array.</i>
	 * @param node the <code>LeafNode</code> or the <code>InterNode</code> to be split
	 */
	private void split(Node node) {
		// if target is root
		if (node.parent == null) {
			// if root is leaf i.e. first ever split
			if (node instanceof LeafNode) {
				LeafNode left = (LeafNode) node;
				left.next = new LeafNode();
				root = new InternalNode();
				((InternalNode) root).children[0] = left;
				((InternalNode) root).children[1] = left.next;

				// calculate midpoint
				int mid = (MAX_SIZE % 2 == 0) ? (MAX_SIZE / 2) : (MAX_SIZE - 1) / 2;

				// transfer keys and pointers to right sibling
				for (int i = mid; i < MAX_SIZE; ++i) {
					left.next.keys[i - mid] = left.keys[i];
					left.next.pointers[i - mid] = left.pointers[i];
					left.keys[i] = null;
					left.pointers[i] = -1;
				}

				left.size = mid;
				left.next.size = (MAX_SIZE % 2 == 0) ? (mid) : (mid + 1);
				left.parent = left.next.parent = (InternalNode) root;
				// promote smallest key from right sibling
				root.keys[0] = left.next.keys[0];
				++root.size;
			} else
			// if root is internal
			{
				InternalNode left = (InternalNode) node;
				InternalNode right = new InternalNode();
				root = new InternalNode();
				((InternalNode) root).children[0] = left;
				((InternalNode) root).children[1] = right;

				// calculate midpoint
				int mid = (BRANCHING_FACTOR % 2 == 0) ? (BRANCHING_FACTOR / 2) : (BRANCHING_FACTOR - 1) / 2;

				// transfer keys and children to right sibling
				for (int i = mid; i < MAX_SIZE; ++i) {
					right.keys[i - mid] = left.keys[i];
					right.children[i - mid] = left.children[i];
					left.keys[i] = null;
					left.children[i] = null;
				}
				// offset for children array being 1 longer than keys array
				right.children[MAX_SIZE - mid] = left.children[MAX_SIZE];
				left.children[MAX_SIZE] = null;
				left.keys[mid - 1] = null;

				left.size = mid - 1;
				right.size = (BRANCHING_FACTOR % 2 == 0) ? (mid - 1) : (mid);
				left.parent = right.parent = (InternalNode) root;
				// promote smallest key from right sibling
				root.keys[0] = right.keys[0];
				++root.size;
			}
		} else {
			// if target is leaf
			if (node instanceof LeafNode) {
				LeafNode left = (LeafNode) node;
				LeafNode right = new LeafNode();

				// calculate midpoint
				int mid = (MAX_SIZE % 2 == 0) ? (MAX_SIZE / 2) : (MAX_SIZE - 1) / 2;

				// transfer keys and pointers to right sibling
				for (int i = mid; i < MAX_SIZE; ++i) {
					right.keys[i - mid] = left.keys[i];
					right.pointers[i - mid] = left.pointers[i];
					left.keys[i] = null;
					left.pointers[i] = -1;
				}

				left.size = mid - 1;
				right.size = (MAX_SIZE % 2 == 0) ? (mid) : (mid + 1);

				// split parent first if needed
				if (!left.parent.hasSpace()) {
					split(left.parent);
				}

				// shift parent keys to make space for promoted key
				int index = left.parent.size;
				while (index > 0 && right.keys[0].compareTo(left.parent.keys[index - 1]) < 0) {
					left.parent.keys[index] = left.parent.keys[index - 1];
					left.parent.children[index + 1] = left.parent.children[index];
					--index;
				}
				// promote left-most key from right to parent
				left.parent.keys[index] = right.keys[0];
				// add right to parent
				left.parent.children[index + 1] = right;
				left.parent.size += 1;

				// assign next and parent
				right.parent = left.parent;
				right.next = left.next;
				left.next = right;
			} else {
				// if target is internal
				InternalNode left = (InternalNode) node;
				InternalNode right = new InternalNode();

				// calculate midpoint
				int mid = (BRANCHING_FACTOR % 2 == 0) ? (BRANCHING_FACTOR / 2) : (BRANCHING_FACTOR - 1) / 2;

				// transfer keys and children to right sibling
				for (int i = mid; i < MAX_SIZE; ++i) {
					right.keys[i - mid] = left.keys[i];
					right.children[i - mid] = left.children[i];
					left.keys[i] = null;
					left.children[i] = null;
				}
				// offset for children array being 1 longer than keys array
				right.children[MAX_SIZE - mid] = left.children[MAX_SIZE];
				left.children[MAX_SIZE] = null;
				left.keys[mid - 1] = null;

				left.size = mid - 1;
				right.size = (BRANCHING_FACTOR % 2 == 0) ? (mid - 1) : (mid);

				// split parent first if needed
				if (!left.parent.hasSpace()) {
					split(left.parent);
				}

				// shift parent keys to make space for promoted key
				int index = left.parent.size;
				while (index > 0 && right.keys[0].compareTo(left.parent.keys[index - 1]) < 0) {
					left.parent.keys[index] = left.parent.keys[index - 1];
					left.parent.children[index + 1] = left.parent.children[index];
					--index;
				}
				// promote left-most key from right to parent
				left.parent.keys[index] = right.keys[0];
				// add right to parent
				left.parent.children[index + 1] = right;
				left.parent.size += 1;

				// assign next and parent
				right.parent = left.parent;
			}
		}
	}
}
