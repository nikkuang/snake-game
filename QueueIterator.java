/**
 * @author Angus Hung, G00867342
 * @assignment Project 2, CIS256
 */

import java.util.Enumeration;

/** Implementation of the Enumeration interface. */
class QueueIterator implements Enumeration {
	/* Maintains next element of iteration. */
	private LinkedListNode next;
	/* Maintains size of iteration. */
	private int size;

	public QueueIterator(LinkedListNode front, int size) {
		next = front;
		this.size = size;
	}
	/** Tests if this enumeration contains more elements. */
	public boolean hasMoreElements() {
		return (size != 0);
	}
	/** Returns next element of iteration, if it exists. */
	public Object nextElement() {
		if (size <= 0) {
			System.out.println("Warning: no elements left to return");
			return null;
		}
		Object tmp = next.value();
		next = next.next();
		size--;
		return tmp;
	}
}
