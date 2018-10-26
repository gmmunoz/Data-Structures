
/*** LinkedList class implements a doubly-linked list. */
//public elements of a private class
	public class MyLinkedList<AnyType> implements Iterable<AnyType> {
		/*** Construct an empty LinkedList. */
		public MyLinkedList() {
			doClear();
		}

		private void clear() {
			doClear();
		}

		/*** Change the size of this collection to zero. */
		public void doClear() {
			//data is null bc it has no value
			beginMarker = new Node<>(null, null, null);
			//beginMarker is used bc it already is null
			endMarker = new Node<>(null, beginMarker, null);
			beginMarker.next = endMarker;
			//now two sentinel nodes are facing each other
			theSize = 0;
			modCount++;
		}

		/***
		 * Returns the number of items in this collection.* @return the number
		 * of items in this collection.
		 */
		public int size() {
			return theSize;
		}

		public boolean isEmpty() {
			return size() == 0;
		}

		/***
		 * Adds an item to this collection, at the end.* @param x any
		 * object.* @return true.
		 */
		//Adds item to the end of list, assuming list based add works
		public boolean add(AnyType x) {
			add(size(), x);
			return true;
		}

		/***
		 * Adds an item to this collection, at specified position.* Items at or
		 * after that position are slid one position higher.* @param x any
		 * object.* @param idx position to add at.* @throws
		 * IndexOutOfBoundsException if idx is not between 0 and size(),
		 * inclusive.
		 */
		//this add method takes the index to which we want to add
		//addBefore takes a node reference not a value
		//getNode is a helper method that takes an index and get the node that corresponds to that index
		public void add(int idx, AnyType x) {
			addBefore(getNode(idx, 0, size()), x);
		}

		/***
		 * Adds an item to this collection, at specified position p.* Items at
		 * or after that position are slid one position higher.* @param p Node
		 * to add before.* @param x any object.* @throws
		 * IndexOutOfBoundsException if idx is not between 0 and size(),
		 * inclusive.
		 */
		private void addBefore(Node<AnyType> p, AnyType x) {
			//x is the data, p.prev is the 
			Node<AnyType> newNode = new Node<>(x, p.prev, p);
			newNode.prev.next = newNode;
			p.prev = newNode;
			theSize++;
			modCount++;
		}

		/***
		 * Returns the item at position idx.* @param idx the index to search
		 * in.* @throws IndexOutOfBoundsException if index is out of range.
		 */
		public AnyType get(int idx) {
			return getNode(idx).data;
		}

		/***
		 * Changes the item at position idx.* @param idx the index to
		 * change.* @param newVal the new value.* @return the old
		 * value.* @throws IndexOutOfBoundsException if index is out of range.
		 */
		//sets old value ---> new value
		public AnyType set(int idx, AnyType newVal) {
			Node<AnyType> p = getNode(idx);
			AnyType oldVal = p.data;
			p.data = newVal;
			return oldVal;
		}

		/***
		 * Gets the Node at position idx, which must range from 0 to size( ) -
		 * 1.* @param idx index to search at.* @return internal node
		 * corresponding to idx.* @throws IndexOutOfBoundsException if idx is
		 * not between 0 and size( ) - 1, inclusive.
		 */
		//this is a helper method
		private Node<AnyType> getNode(int idx) {
			//takes in 3 inputs (index being searched, bounds of list)
			return getNode(idx, 0, size() - 1);
		}

		/***
		 * Gets the Node at position idx, which must range from lower to
		 * upper.* @param idx index to search at.* @param lower lowest valid
		 * index.* @param upper highest valid index.* @return internal node
		 * corresponding to idx.* @throws IndexOutOfBoundsException if idx is
		 * not between lower and upper, inclusive.
		 */
		//invoke this method when we need more info
		private Node<AnyType> getNode(int idx, int lower, int upper) {
			Node<AnyType> p;
			//check to make sure index is not outside of upper/lower bounds
			if (idx < lower || idx > upper)
				throw new IndexOutOfBoundsException("getNode index: " + idx + "; size: " + size());
			//checks to see if index is less than size/2 --> decides which direction to start from
			//if its in first half
			if (idx < size() / 2) {
				p = beginMarker.next;
				for (int i = 0; i < idx; i++)
					p = p.next;
				//if its in second half of list
			} else {
				p = endMarker;
				for (int i = size(); i > idx; i--)
					p = p.prev;
			}
			//p is the reference to the node of the index that was put in
			return p;
		}

		/***
		 * Removes an item from this collection.
		 * 
		 * @param idx
		 *            the index of the object.* @return the item was removed
		 *            from the collection.
		 */
		public AnyType remove(int idx) {
			return remove(getNode(idx));
		}

		/***
		 * Removes the object contained in Node p.* @param p the Node containing
		 * the object.* @return the item was removed from the collection.
		 */
		private AnyType remove(Node<AnyType> p) {
			p.next.prev = p.prev;
			p.prev.next = p.next;
			theSize--;
			modCount++;
			return p.data;
			//tells what was removed p.data
		}

		/*** Returns a String representation of this collection. */
		public String toString() {
			StringBuilder sb = new StringBuilder("[ ");
			for (AnyType x : this)
				sb.append(x + "  ");
			sb.append("]");
			return new String(sb);
		}

		/***
		 * Obtains an Iterator object used to traverse the collection.* @return
		 * an iterator positioned prior to the first element.
		 */
		public java.util.Iterator<AnyType> iterator() {
			return new LinkedListIterator();
		}

		/***
		 * This is the implementation of the LinkedListIterator.* It maintains a
		 * notion of a current position and of* course the implicit reference to
		 * the MyLinkedList.
		 */
		//required to have a method called iterator due to implementing Iterator interface
		
		//LinkedListIterator instantiates -- NON STATIC nested CLASS
		//due to being non static it can access endMarker and beginMarker and that class
		private class LinkedListIterator implements java.util.Iterator<AnyType> {
			private Node<AnyType> current = beginMarker.next;
			//ignore modCount 
			private int expectedModCount = modCount;
			//you have to have seen something to be able to remove it!!!!!
			private boolean okToRemove = false;

			public boolean hasNext() {
				return current != endMarker;
			}

			public AnyType next() {
				if (modCount != expectedModCount)
					throw new java.util.ConcurrentModificationException();
				if (!hasNext())
					throw new java.util.NoSuchElementException();
				//pulls value out
				AnyType nextItem = current.data;
				//advances reference to the next in the list
				current = current.next;
				//once element is passed, it is safe to be deleted
				okToRemove = true;
				return nextItem;
			}

			public void remove() {
				if (modCount != expectedModCount)
					throw new java.util.ConcurrentModificationException();
				//check to see if it is safe to remove object b4 we delete it
				if (!okToRemove)
					throw new IllegalStateException();
				//just calls old method bc there is no point on writing extra code
				MyLinkedList.this.remove(current.prev);
				expectedModCount++;
				okToRemove = false;
			}
		}

		/*** This is the doubly-linked list node. */
		private static class Node<AnyType> {
			public Node(AnyType d, Node<AnyType> p, Node<AnyType> n) {
				data = d;
				prev = p;
				next = n;
			}

			public AnyType data;
			public Node<AnyType> prev;
			public Node<AnyType> next;
		}

		private int theSize;
		private int modCount = 0;
		private Node<AnyType> beginMarker;
		private Node<AnyType> endMarker;
	}

	class TestLinkedList {
		public static void main(String[] args) {
			MyLinkedList<Integer> lst = new MyLinkedList<>();
			for (int i = 0; i < 10; i++)
				lst.add(i);
			for (int i = 20; i < 30; i++)
				lst.add(0, i);
			lst.remove(0);
			lst.remove(lst.size() - 1);
			System.out.println(lst);
			java.util.Iterator<Integer> itr = lst.iterator();
			while (itr.hasNext()) {
				itr.next();
				itr.remove();
				System.out.println(lst);
			}
		}
	}
}
