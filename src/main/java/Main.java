import Utils.HashTable;

import java.util.ArrayList;

public class Main {
	public static void main(String args[]) {
		HashTable<String> hashTable = new HashTable<>();
		for (int i = 0; i < 6; i++) {
			hashTable.add(i + "");
		}
		hashTable.remove(new String[] {"2", "3", "4"});

//		ArrayList<String> strings = new ArrayList<>();
//		strings.add("s");
	}
}
