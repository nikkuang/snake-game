import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Vector;

/** Implementation of the Queue interface using a linked list. */
class QueueLL extends Vector implements Queue  {
	/* A QueueLL maintains references to the front and back nodes of the queue. */
	private LinkedListNode front = null;
	private LinkedListNode back = null;
	/* A QueueLL maintains the size of the queue. */
	private int size = 0;
	/** Dummy default constructor. */
	public QueueLL() { }
	/** Adds object to the queue. Adds to back. */
	public void enqueue(Object value) {
		LinkedListNode nw = new LinkedListNode(value, back, null);
		back = nw;
		size++;
		if (size == 1) {
			front = back;
			back = front;
		}
	}
	/** Removes Object from the queue. Removes from front. Returns Object removed. */
	public Object dequeue() {
		if (!isEmpty()) {
			size--;
			LinkedListNode tmp = front;
			front = front.next();
			if (front == null) { return tmp.value(); /* queue has become empty */ }
			front.setPrev(null);
			tmp.setNext(null);
			tmp.setPrev(null);

			if (size == 1) {
				front = back;
				back = front;
			}
			return tmp.value();
		} else {
			System.out.println("Warning: cannot remove from empty queue.");
			return null;
		}
	}
	/** Predicate function for determining whether queue is empty. */
	public boolean isEmpty() {
		return (size == 0);
	}

	/** Implements Vector's elements() method. */
	@Override
	public Enumeration elements() {
		return (Enumeration) (new QueueIterator(front, size));
	}
	/** Getter for the length of the queue. */
	public int size() {
		return size;
	}
	/** Getter for the object at the front of the queue. */
	public Object front() {
		return front.value();
	}
}
