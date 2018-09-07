package Utils;

import org.junit.jupiter.api.Test;

import Utils.HashTable.Modifications;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {
	HashTable<String> hashTable;

	@Test
	void TableShouldManipulateElementsAndModifyOwnSize() {
		hashTable = new HashTable<>();
		HashTable<String> spy = spy(hashTable);

		for (int i = 0; i < 6; ++i) {
			spy.add(i + "");
		}
		assertAll("element manipulation",
				() -> assertEquals(6, spy.getSize(), "Size after table growth did not match expected size"),
				() -> assertTrue("3".equalsIgnoreCase(spy.remove("3")), "Table did not remove expected object"),
				() -> assertEquals(5, spy.getSize(), "Size after removal did not match expected size"),
				() -> assertThrows(java.util.NoSuchElementException.class, () -> spy.remove("3")));
		spy.remove(new String[] { "2", "4", "5" });
		assertEquals(4, spy.getCapacity(), "Table did not shrink properly");

		verify(spy, atLeastOnce().description("Table did not grow as expected")).modify(Modifications.valueOf("GROW"));
		verify(spy, atLeastOnce().description("Table did not shrink as expected"))
				.modify(Modifications.valueOf("SHRINK"));
	}
}
