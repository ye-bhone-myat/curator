package Utils;

/**
 * The Constants interface provides a centralized way of controlling the behavior of classes within the Utils package
 */
public interface Constants {
 int BRANCHING_FACTOR = 8;	// the branching factor for the BPlusTree
 int MAX_SIZE = BRANCHING_FACTOR - 1;	// the maximum size of the keys array of the Node class
}
