package Utils;

import java.io.Serializable;

public class HashTable<E> implements Serializable {
	private int size = 16;
	private HashElement[] table;
	private int count;

	public HashTable() {
		table = new HashElement[size];
		count = 0;
	}

	public void add(E e) {
		HashElement<E> element = new HashElement<E>(e);
		String key = element.getKey();
		int hashCode = findSlot(key);
		// if key is already indexed
		// increment value
		if (table[hashCode] != null) {
			table[hashCode].increment();
		} // if key has not been indexed
		else {
			table[hashCode] = element;
			++count;
			if (count >= (0.8 * size)) {
				grow();
			}
		}
	}

	public Object remove(String key) {
		Object removed = getElement(key);
		int index = findSlot(key);
		while (table[index] != null) {
			int nextIndex = (index + 1) % size;
			table[index] = table[nextIndex];
			index = nextIndex;
		}
		return removed;
	}

	public Object getElement(String key) {
		return table[findSlot(key)].object;
	}

	private int findSlot(String key) {
		int index = key.hashCode() % size;
		while (table[index] != null && !table[index].getKey().equalsIgnoreCase(key)) {
			index = (index + 1) % size;
		}
		return index;
	}

	private void grow() {
		size *= 2;
		HashElement[] newTable = new HashElement[size];
		for (HashElement element : table) {
			if (element != null) {
				String key = element.getKey();
				int hashCode = this.findSlot(key);
				newTable[hashCode] = element;
			}
		}
		table = newTable;
	}

	private class HashElement<T> implements Comparable, Serializable {

		final T object;
		private final String key;
		private int value;

		HashElement(T o) {
			object = o;
			key = object.toString();
			value = 1;
		}

		void increment() {
			if (value < 256) {
				value++;
			}
		}

		String getKey() {
			return key;
		}

		int getVal() {
			return value;
		}

		@SuppressWarnings("unchecked")
		public int compareTo(Object o) {
			HashElement other = (HashElement) o;
			return this.getKey().compareTo(other.getKey());
		}
	}


}
