import java.util.Iterator;

@SuppressWarnings("unchecked")

public class ArraySet<T> implements Set<T> {

	public static final int CAPACITY_MULTIPLIER = 2;
	public static final int DEFAULT_CAPACITY = 15;
	
	private int capacity = 0;
	private int numberOfItems = 0;
	private T[] items;
	
	public ArraySet() {
		this(DEFAULT_CAPACITY);
	}
	
	public ArraySet(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Capacity must be >= 0");
		}
		
		this.capacity = size;
		items = (T[])new Object[size];
	}
	
	@Override
	public void add(T element) {
		if(contains(element)== false) {
			ensureCapacity();
			items[numberOfItems] = element;
			numberOfItems++;
		}
	}

	@Override
	public void addAll(T[] elements) {
		for (int i = 0; i < elements.length; i++) {
			add(elements[i]);	
		}
	}

	@Override
	public boolean contains(T item) {
		if (indexOf(item) > -1)
			return true;
		else
			return false;
	}

	@Override
	public int getSize() {
		return numberOfItems;
	}

	@Override
	public void remove(T item) {
		int index = indexOf(item);
		
		if (index > -1) {
			numberOfItems--;
			items[index] = items[numberOfItems];
		}
	}

	@Override
	public Set<T> union(Set<T> anotherSet) {
		Set<T> blankSet = new ArraySet<T>();
		Set<T> newSet = new ArraySet<T>();
		
		newSet = anotherSet.difference(blankSet);
		
		for(int i = 0; i < getSize(); i++) {
			newSet.add(items[i]);
		}
		return newSet;
	}

	@Override
	public Set<T> intersection(Set<T> anotherSet) {
		Set<T> newSet = new ArraySet<T>();
		
		for(int i = 0; i < getSize(); i++) {
			if(anotherSet.contains(items[i])) {
				newSet.add(items[i]);
			}
		}
		return newSet;
	}

	@Override
	public Set<T> difference(Set<T> anotherSet) {
		Set<T> newSet = new ArraySet<T>();
		for(int i = 0; i < getSize(); i++) {
			if(anotherSet.contains(items[i]) == false){
				newSet.add(items[i]);
				System.out.println(items[i]);
			}
		}
		return newSet;
	}
	
	private void ensureCapacity() {
		if (numberOfItems == capacity) {
			T[] newArray = (T[]) new Object[(numberOfItems+1) * CAPACITY_MULTIPLIER];
			System.arraycopy(items,0,newArray,0,numberOfItems);
			items = newArray;
		}
	}
	
	private int indexOf(T element) {
		int index = -1;
		for (int i = 0; i < numberOfItems; i++) {
			if (items[i].equals(element)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public Iterator<T> iterator(){
		return new ArrayBagIterator();
	}
	
	private class ArrayBagIterator implements Iterator<T>{
		private int index = 0;
		
		/**
		 * Determines if there are more elements
		 * in the iteration.
		 * 
		 * @return true if there are more elements, false otherwise.
		 */
		public boolean hasNext() {
			if (index < numberOfItems)
				return true;
			else
				return false;
		}

		/**
		 * Returns the next element in the iteration.
		 * 
		 * @throws java.util.NoSuchElementException if there are no more elements in the iteration.
		 */
		public T next() {
			if (hasNext()) {
				T nextItem = items[index];
				index++;
				
				return nextItem;
			}
			else
				throw new java.util.NoSuchElementException("No items remaining in the iteration.");
			
		}

		/**
		 * The remove() operation is not supported.
		 * @throws UnsupportedOperationException if involed.
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
