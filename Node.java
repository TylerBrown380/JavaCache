/**
 * node used for building linked lists.
 * 
 * @author TylerBrown
 * @param <T>
 */
public class Node<T> {
	private Node<T> next;
	private Node<T> previous;
	private T element;

	/**
	 * Node constructor which takes in an element.
	 * 
	 * @param element
	 */
	public Node(T element) {
		next = null;
		this.element = element;
	}

	/**
	 * @return the next
	 */
	public Node<T> getNext() {
		return next;
	}

	/**
	 * @param next
	 * the next to set
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}

	/**
	 * @return the element
	 */
	public T getElement() {
		return element;
	}

	/**
	 * @param element
	 * the element to set
	 */
	public void setElement(T element) {
		this.element = element;
	}

	/**
	 * @return previous element
	 */
	public Node<T> getPrevious() {
		return previous;

	}

	/**
	 * sets the previous element
	 * @param previous
	 */
	public void setPrevious(Node<T> previous) {
		this.previous = previous;
	}

}
