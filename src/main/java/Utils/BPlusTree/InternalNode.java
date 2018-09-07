package Utils.BPlusTree;

class InternalNode extends Node {
	Node[] children;
	InternalNode(){
		super();
		children = new Node[BRANCHING_FACTOR];
	}

	void add(String key){
		int i = size;
		while (key.compareToIgnoreCase(keys[i - 1]) < 0) {
			keys[i] = keys[i - 1];
			--i;
		}
		keys[i] = key;
		++ size;
	}

}
