package Utils.BPTree;

public class InternalNode extends Node {
	Node[] children;
	public InternalNode(){
		super();
		children = new Node[BRANCHING_FACTOR];
	}

	@Override
	Node insert(String key) {
		return null;
	}

	void add(String key){
		int i = size;
		while (key.compareToIgnoreCase(keys[i - 1]) < 0) {
			keys[i] = keys[i - 1];
			--i;
		}
		keys[i] = key;
	}
}
