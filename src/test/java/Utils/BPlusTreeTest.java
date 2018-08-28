package Utils;

import Utils.BPlusTree.Tree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BPlusTreeTest {
	static String[] keys = new String[100];
	static long[] pointers = new long[100];
	Tree tree = new Tree();

	@BeforeEach
	void setup() {
		for (int i = 0; i < 100; ++i) {
			keys[i] = (i + 1) + "";
			pointers[i] = i;
		}
	}

	@Test
	void TreeSizeShouldIncrementPerAddCall() {
		for (int i = 0; i < 100; ++i) {
			tree.add(keys[i], pointers[i]);
			assertEquals(i + 1, tree.getSize());
		}
	}

	@Test
	void TreeShouldReturnCorrectSearchResult() {
		assertTrue(tree.search("0") == -1);
		for (int i = 1; i < 101; ++i) {
			assertEquals(i, tree.search(String.valueOf(i)));
		}
	}

}
