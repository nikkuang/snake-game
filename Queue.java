/** Interface for Queue. */
interface Queue {
	/** Adds object to the queue. Adds to back of the queue. */
	void enqueue(Object value);
	/** Removes Object from the queue. Removes from front of the queue. Returns object removed. */
	Object dequeue();
	/** Predicate function for determining whether queue is empty. */
	boolean isEmpty();
	/** Getter for the length of the queue. */
	int size();
	/** Getter for the object at the front of the queue. */
	Object front();
}
