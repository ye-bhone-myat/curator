package Utils.BPlusTree;

import Utils.Constants;

abstract class Node implements Constants{
    String[] keys;
    int size;
    InternalNode parent;

    Node(){
        size = 0;
        keys = new String[BRANCHING_FACTOR - 1];
    }

    boolean hasSpace(){
        return size < MAX_SIZE;
    }

    boolean isEmpty(){
        return size == 0;
    }

//    abstract Node insert(String key);


}
