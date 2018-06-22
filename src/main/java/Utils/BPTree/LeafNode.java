package Utils.BPTree;

public class LeafNode extends Node {
	LeafNode next;
	long[] pointers;

	public LeafNode() {
		super();
		pointers = new long[keys.length];
	}

	@Override
	Node insert(String key) {
		if (size < BRANCHING_FACTOR - 1) {
			add(key);
			return this;
		} else {
			InternalNode parent = new InternalNode();
			LeafNode sibling = new LeafNode();
			sibling.next = this.next;
			this.next = sibling;
			int mid = (MAX_SIZE % 2 == 0) ? (MAX_SIZE / 2) : (MAX_SIZE - 1) / 2;

			int keyIndex = MAX_SIZE;
			do {
				-- keyIndex;
			} while (key.compareTo(this.keys[keyIndex]) < 0);
			++ keyIndex;

			for (int i = mid; i < MAX_SIZE; ++i) {
				sibling.keys[i - mid] = this.keys[i];
			}

			if (keyIndex < mid) {
				this.add(key);
			} else {
				sibling.add(key);
			}

			parent.add(sibling.keys[0]);

		}
	}

	private void add(String key) {
		int i = size;
		while (key.compareToIgnoreCase(keys[i - 1]) < 0) {
			keys[i] = keys[i - 1];
			--i;
		}
		keys[i] = key;
	}

}
