/**
 * @author Angus Hung, G00867342
 * @assignment Project 2, CIS256
 */
/** Class that represents a node in a linked list. */
class LinkedListNode {
	/* A linked list node points to the previous and next nodes. */
	private LinkedListNode prev = null;
	private LinkedListNode next = null;
	/* A linked list node holds a value. */
	Object value = null;
	/** Constructor for a linked list node: sets VALUE to node's own value. */
	public LinkedListNode(Object value) {
		this.value = value;
	}
	/** Another constructor for setting the references to PREV and NEXT, in
	 * addition to VALUE. If PREV/NEXT is not null, then the references of the * PREV and NEXT nodes are set to point to THIS node. */
	public LinkedListNode(Object value, LinkedListNode prev, LinkedListNode next) {
		this.value = value;
		this.prev = prev;
		this.next = next;
		if (prev != null)
			prev.setNext(this);
		if (next != null)
			next.setPrev(this);
	}
	/** Setter for NEXT. */
	public void setNext(LinkedListNode node) {
		this.next = node;
	}
	/** Setter for PREV. */
	public void setPrev(LinkedListNode prev) {
		this.prev = prev;
	}
	/** Getter for NEXT. */
	public LinkedListNode next() {
		return next;
	}
	/** Getter for PREV. */
	public LinkedListNode prev() {
		return prev;
	}
	/** Getter for VALUE. */
	public Object value() {
		return value;
	}
}
