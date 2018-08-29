package Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {
	HashTable<String> hashTable;

	@Test
	void TableShouldManipulateElementsAndModifyOwnSize() {
		hashTable = new HashTable<>();
		for (int i = 0; i < 6; ++i) {
			hashTable.add(i + "");
		}
		assertEquals(8, hashTable.getCapacity(), "Table did not grow properly");
		assertAll("element manipulation",
				() -> assertEquals(6, hashTable.getSize(), "Size after table growth did not match expected size"),
				() -> assertTrue("3".equalsIgnoreCase(hashTable.remove("3")), "Table did not remove expected object"),
				() -> assertEquals(5, hashTable.getSize(), "Size after removal did not match expected size"),
				() -> assertThrows(java.util.NoSuchElementException.class, () -> hashTable.remove("3"))
		);
		hashTable.remove(new String[]{"2", "4", "5"});
		assertEquals(4, hashTable.getCapacity(), "Table did not shrink properly");
	}

}
