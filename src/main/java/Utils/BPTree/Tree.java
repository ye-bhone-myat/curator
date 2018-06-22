package Utils.BPTree;

import Utils.Constants;

public class Tree implements Constants {

	private Node root;

	public Node search(String key) {
		return tree_search(key, root);
	}

	private Node tree_search(String key, Node node) {
		if (node instanceof LeafNode) {
			return node;
		} else {
			int i = node.size;
			do {
				--i;
			} while (key.compareTo(node.keys[i]) < 0);
			++i;
			return tree_search(key, ((InternalNode) node).children[i]);
		}
	}

	public void insert(String key, long value) {

	}

	public Node add(Node node, String key, long value) {
		if (node instanceof LeafNode) {
			if (node.size < BRANCHING_FACTOR - 1) {
				for (int i = node.size; key.compareTo(node.keys[i - 1]) < 0; --i) {
					node.keys[i] = node.keys[i - 1];
				}
				node.keys[node.size] = key;
				++node.size;
				return node;
			} else {
				InternalNode parent = new InternalNode();
				LeafNode sibling = new LeafNode();
				sibling.next = ((LeafNode) node).next;
				((LeafNode) node).next = sibling;
				int mid = (MAX_SIZE % 2 == 0) ? (MAX_SIZE / 2) : (MAX_SIZE - 1) / 2;

				int keyIndex = MAX_SIZE;
				do {
					-- keyIndex;
				} while (keyIndex >= 0 && key.compareTo(node.keys[keyIndex]) < 0);
				++ keyIndex;


				for (int i = mid; i < MAX_SIZE; ++i) {
					sibling.keys[i - mid] = node.keys[i];
					sibling.pointers[i - mid] = ((LeafNode) node).pointers[i];
				}
				node.size = (int) Math.floor(MAX_SIZE/2);
				sibling.size = (int) Math.ceil(MAX_SIZE/2);

				if (keyIndex < mid) {
					// add to node
				} else {
					// add to sibling
				}

				// add to parent
				return parent;
			}
		} else {
			InternalNode internal = (InternalNode) node;
			int i = BRANCHING_FACTOR;
			do{
				-- i;
			}while (i >= 0 && key.compareTo(internal.keys[i]) < 0);
			++ i;
			Node result = add(internal.children[i], key, value);
			if (result instanceof LeafNode){
				internal.children[i] = result;
				return internal;
			}
		}
	}

	void addToLeaf(LeafNode node, String key, long value){
		//TODO: figure out where to update the size of Nodes
	}

	void addToInternal(InternalNode node, String key){

	}

	void addKey(Node node, String key){

	}


}
