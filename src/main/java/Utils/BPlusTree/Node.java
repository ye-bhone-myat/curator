package Utils.BPlusTree;

import Utils.Constants;

/**
 * The Node class provides a framework for the <code>LeafNode</code> and <code>InternalNode</code> classes.
 * The <code>size</code> variable for this class denotes the size of the <code>keys</code> array only.
 */
abstract class Node implements Constants{
    String[] keys;
    int size;   // only tracks the size of the keys array
    InternalNode parent;

    Node(){
        size = 0;
        keys = new String[MAX_SIZE];
    }

    boolean hasSpace(){
        return size < MAX_SIZE;
    }

    boolean isEmpty(){
        return size == 0;
    }

//    abstract Node insert(String key);


}
