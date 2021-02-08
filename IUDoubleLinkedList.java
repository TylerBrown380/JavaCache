import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author TylerBrown This class creates a double linked list and provides
 *         methods to add/remove/set etc. the list using Nodes as references to
 *         elements.
 * @param <T>
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {

	private Node<T> head;
	private Node<T> tail;
	private int size;
	private int modCount;

	/**
	 * The constructor for a doubley linked list, it sets head and tail to null, and
	 * sets up the size and current modcount.
	 */
	public IUDoubleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		ListIterator<T> lit = listIterator();
		lit.add(element);
	}

	@Override
	public void addToRear(T element) {
		Node<T> newNode = new Node<T>(element);
		if (tail != null) {
			tail.setNext(newNode);
		} else {
			head = newNode;
		}
		newNode.setPrevious(tail);
		tail = newNode;
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		Node<T> targetNode = head;
		while (targetNode != null && !targetNode.getElement().equals(target)) {
			targetNode = targetNode.getNext();
		}
		if (targetNode == null) {
			throw new NoSuchElementException();
		}
		Node<T> newNode = new Node<T>(element);
		newNode.setNext(targetNode.getNext());
		newNode.setPrevious(targetNode);
		targetNode.setNext(newNode);
		if (targetNode != tail) {
			newNode.getNext().setPrevious(newNode);
		} else {
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		ListIterator<T> lit = listIterator(index);
		lit.add(element);
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T element = head.getElement();
		head = head.getNext();

		if (head == null) {
			tail = null;
		} else {
			head.setPrevious(null);
		}
		size--;
		modCount++;
		return element;
	}

	@Override
	public T removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		tail = tail.getPrevious();
		if (tail != null) {
			tail.setNext(null);
		} else {
			head = null;
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		Node<T> targetNode = head;
		while (targetNode != null && !targetNode.getElement().equals(element)) {
			targetNode = targetNode.getNext();
		}
		if (targetNode == null) {
			throw new NoSuchElementException();
		}
		T retVal = targetNode.getElement();
		if (targetNode != head) {
			targetNode.getPrevious().setNext(targetNode.getNext());
		} else {
			head = head.getNext();
		}
		if (targetNode != tail) {
			targetNode.getNext().setPrevious(targetNode.getPrevious());
		} else {
			tail = tail.getPrevious();
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		T retVal = null;
		ListIterator<T> lit = listIterator(index);
		retVal = lit.next();
		lit.remove();
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if (index > size - 1 || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> targetNode = head;
		for (int i = 0; i < index; i++) {
			targetNode = targetNode.getNext();
		}
		targetNode.setElement(element);
		modCount++;
	}

	@Override
	public T get(int index) {
		if (index > size - 1 || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> targetNode = head;
		for (int i = 0; i < index; i++) {
			targetNode = targetNode.getNext();
		}
		T retVal = targetNode.getElement();
		return retVal;
	}

	@Override
	public int indexOf(T element) {
		int retVal = 0;
		Node<T> targetNode = head;
		for (int i = 0; i < size; i++) {
			if (targetNode.getElement() == element) {
				retVal = i;
				return retVal;
			}
			targetNode = targetNode.getNext();
		}
		return -1;
	}

	@Override
	public T first() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		Node<T> targetNode = head;
		for (int i = 0; i < size; i++) {
			if (targetNode.getElement().equals(target)) {
				return true;
			}
			targetNode = targetNode.getNext();
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public int size() {
		return size;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();

		str.append("[");

		Node<T> current = head;

		while (current != null) {
			str.append(current.getElement().toString());
			str.append(", ");
			current = current.getNext();
		}
		if (!isEmpty()) {
			str.delete(str.length() - 2, str.length());
		}

		str.append("]");

		return str.toString();
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		// TODO Auto-generated method stub
		return new DLLIterator(startingIndex);
	}

	/**
	 * ListIterator for IUDoubleLinkedList
	 */
	private class DLLIterator implements ListIterator<T> {
		private Node<T> nextNode;
		private int nextIndex;
		private Node<T> lastReturned;
		private int iterModCount;

		public DLLIterator() {
			this(0);
		}

		/**
		 * @param startingIndex
		 *            Constructor used for ListIterator that lets you insert at an index
		 */
		public DLLIterator(int startingIndex) {
			if (startingIndex < 0 || startingIndex > size) {
				throw new IndexOutOfBoundsException();
			}
			nextNode = head;
			for (int i = 0; i < startingIndex; i++) {
				nextNode = nextNode.getNext();
			}
			nextIndex = startingIndex;
			lastReturned = null;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != null);
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastReturned = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			return lastReturned.getElement();

		}

		@Override
		public boolean hasPrevious() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != head);
		}

		@Override
		public T previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if (nextNode == null) {
				nextNode = tail;
			} else {
				nextNode = nextNode.getPrevious();
			}
			lastReturned = nextNode;
			nextIndex--;
			return lastReturned.getElement();
		}

		@Override
		public int nextIndex() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex - 1;
		}

		@Override
		public void remove() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			if (head == tail) { // only one element
				head = tail = null;
			} else if (lastReturned == head) { // removing head
				head = head.getNext();
				head.setPrevious(null);
			} else if (lastReturned == tail) { // removing tail
				tail = tail.getPrevious();
				tail.setNext(null);
			} else { // for things in the middle
				lastReturned.getPrevious().setNext(lastReturned.getNext());
				lastReturned.getNext().setPrevious(lastReturned.getPrevious());
			}
			if (nextNode == lastReturned) {
				nextNode = nextNode.getNext();
			} else { // last move was next
				nextIndex--;
			}
			lastReturned = null; // can't remove stuff twice
			size--;
			modCount++;
			iterModCount++;
		}

		@Override
		public void set(T e) {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			lastReturned.setElement(e);
			modCount++;
			iterModCount++;
		}

		@Override
		public void add(T e) {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			Node<T> newNode = new Node<T>(e);
			newNode.setNext(nextNode);
			if (size == 0) {
				head = tail = newNode;
			} else if (nextNode == null) {
				tail.setNext(newNode);
				newNode.setPrevious(tail);
				tail = newNode;
			} else {
				newNode.setPrevious(nextNode.getPrevious());
				nextNode.setPrevious(newNode);
				if (nextNode == head) {
					head = newNode;
				} else {
					newNode.getPrevious().setNext(newNode);
				}
			}
			size++;
			modCount++;
			iterModCount++;
		}

	}
}
