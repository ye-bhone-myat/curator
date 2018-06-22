package Utils.BPTree;

import Utils.Constants;

abstract class Node implements Constants{
    String[] keys;
    int size;

    public Node(){
        size = 0;
        keys = new String[BRANCHING_FACTOR - 1];
    }

    abstract Node insert(String key);

}
