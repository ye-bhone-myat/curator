package Utils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static Utils.HashTable.Modifications.GROW;
import static Utils.HashTable.Modifications.SHRINK;


public class HashTable<E> implements Serializable {
	private static final long serialVersionUID = 1425567225662528520L;
	private int size = 4;
	private HashElement<E>[] table;
	private int count;

	// @SuppressWarnings("unchecked")
	public HashTable() {
		table = new HashElement[size];
		count = 0;
	}

	/**
	 * Does what it says on the tin
	 * @return the current size of the hash table
	 */
	public int getSize() {
		return count;
	}

	/**
	 * For testing purposes only
	 * @return the size of the array being used as the table
	 */
	public int getCapacity(){
		return size;
	}

	/**
	 * Adds a single element to the table
	 * @param e the elment to be added
	 */
	public void add(E e) {
		HashElement<E> element = new HashElement<>(e);
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
				modify(GROW);
			}
		}
	}

	/**
	 * Adds elements from a collection to the table
	 * @param c collection of elements to be added
	 */
	public void add(Collection<E> c){
		c.forEach(this::add);
	}

	/**
	 * Adds elements from an array to the table
	 * @param a array of elements to be added
	 */
	public void add(E[] a){
		for (E e : a) {
			add(e);
		}
	}

	/**
	 * Retrieves an element with the specified <code>key</code> from the table, then removes the element from the table.
	 * If an element with the corresponding <code>key</code> does not exist, a <code>NoSuchElementException</code> is
	 * thrown.
	 * @param key the key of the element to search for
	 * @return the element with the corresponding <code>key</code>
	 * @throws NoSuchElementException
	 */
	public E remove(String key){
		E removed = getElement(key);

		int index = findSlot(key);
		while (table[index] != null) {
			table[index] = null;
			int nextIndex = (index + 1) % size;
			if (table[nextIndex] != null) {
				if (findSlot(table[nextIndex].key) == index) {
					table[index] = table[nextIndex];
				} else {
					break;
				}
			} else {
				break;
			}
			index = nextIndex;
		}
		--count;
		if (count < 0.4 * size) {
			modify(SHRINK);
		}
		return removed;
	}

	/**
	 * Retrieves and removes elements from the table with corresponding keys from a given collection.
	 *  A <code>NoSuchElementException</code> is thrown if there is no element in the table for one or more of the keys
	 * @param c collection of keys to elements that are to be removed
	 * @return a collection of removed elements
	 * @throws NoSuchElementException
	 */
	public Collection<E> remove(Collection<String> c){
		List<E> list = new ArrayList<>();
		c.forEach(
				s -> list.add(remove(s))
		);
		return list;
	}

	/**
	 * Retrieves and removes elements from the table with corresponding keys from a given array.
	 *  A <code>NoSuchElementException</code> is thrown if there is no element in the table for one or more of the keys
	 * @param a array of keys to elements that are to be removed
	 * @return a collection of removed elements
	 * @throws NoSuchElementException
	 */
	public Collection<E> remove(String[] a){
		List<String> list = Arrays.asList(a);
		return remove(list);
	}

	/**
	 * Retrieves the element with the corresponding <code>key</code>. Throws a <code>NoSuchElementException</code>
	 *  if an element is not found.
	 * @param key the key of the target element
	 * @return the element associated with the <code>key</code>
	 * @throws NoSuchElementException
	 */
	E getElement(String key) throws NoSuchElementException{
		if (table[findSlot(key)] == null) {
			throw new NoSuchElementException();
		}
		return (E) table[findSlot(key)].object;
	}

	/**
	 * Finds a vacant slot in the table for the given key
	 * @param key the key used to find the slot with
	 * @return the appropriate slot for the key
	 */
	int findSlot(String key) {
		int index = key.hashCode() % size;
		if (index < 0){
			index *= (-1);
		}
		while (table[index] != null && !table[index].getKey().equalsIgnoreCase(key)) {
			index = (index + 1) % size;
		}

		return index;
	}

	/**
	 * Grows or shrinks the table
	 * @param m <code>GROW</code> or <code>SHRINK</code>
	 * @see Modifications
	 */
	@SuppressWarnings("unchecked")
	void modify(Modifications m) {
		if (m == GROW) {
			size *= 2;
		} else if (m == SHRINK) {
			size /= 2;
		}
		HashElement<E>[] oldTable = table;
		table = new HashElement[size];
		for (HashElement<E> element : oldTable) {
			if (element != null) {
				String key = element.getKey();
				int hashCode = this.findSlot(key);
				table[hashCode] = element;
			}
		}
	}

	public List<E> getList(){
		return Arrays.asList(table).stream().filter(s -> s != null)
				.map(s -> s.object)
				.collect(Collectors.toCollection(ArrayList::new));
	}


	enum Modifications {
		GROW, SHRINK
	}

	class HashElement<T> implements Comparable<HashElement<T>>, Serializable {

		private static final long serialVersionUID = 1290435324407032917L;	// added to meet Java Object Serialization Specification
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

		public int compareTo(HashElement<T> o) {
			HashElement<T> other = o;
			return this.getKey().compareTo(other.getKey());
		}
	}

}
