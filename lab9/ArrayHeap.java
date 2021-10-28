import java.util.ArrayList;

/** A Generic heap class. Unlike Java's priority queue, this heap doesn't just
  * store Comparable objects. Instead, it can store any type of object
  * (represented by type T) and an associated priority value.
  * @author CS 61BL Staff */
public class ArrayHeap<T> {

	/* DO NOT CHANGE THESE METHODS. */

	/* An ArrayList that stores the nodes in this binary heap. */
	private ArrayList<Node> contents;

	/* A constructor that initializes an empty ArrayHeap. */
	public ArrayHeap() {
		contents = new ArrayList<>();
		contents.add(null);
	}

	/* Returns the node at index INDEX. */
	private Node getNode(int index) {
		if (index >= contents.size()) {
			return null;
		} else {
			return contents.get(index);
		}
	}

	private void setNode(int index, Node n) {
		// In the case that the ArrayList is not big enough
		// add null elements until it is the right size
		while (index + 1 >= contents.size()) {
			contents.add(null);
		}
		contents.set(index, n);
	}

	/* Swap the nodes at the two indices. */
	private void swap(int index1, int index2) {
		Node node1 = getNode(index1);
		Node node2 = getNode(index2);
		this.contents.set(index1, node2);
		this.contents.set(index2, node1);
	}

	/* Prints out the heap sideways. Use for debugging. */
	@Override
	public String toString() {
		return toStringHelper(1, "");
	}

	/* Recursive helper method for toString. */
	private String toStringHelper(int index, String soFar) {
		if (getNode(index) == null) {
			return "";
		} else {
			String toReturn = "";
			int rightChild = getRightOf(index);
			toReturn += toStringHelper(rightChild, "        " + soFar);
			if (getNode(rightChild) != null) {
				toReturn += soFar + "    /";
			}
			toReturn += "\n" + soFar + getNode(index) + "\n";
			int leftChild = getLeftOf(index);
			if (getNode(leftChild) != null) {
				toReturn += soFar + "    \\";
			}
			toReturn += toStringHelper(leftChild, "        " + soFar);
			return toReturn;
		}
	}

	/* A Node class that stores items and their associated priorities. */
	public class Node {
		private T item;
		private double priority;

		private Node(T item, double priority) {
			this.item = item;
			this.priority = priority;
		}

		public T item(){
			return this.item;
		}

		public double priority() {
			return this.priority;
		}

		@Override
		public String toString() {
			return this.item.toString() + ", " + this.priority;
		}
	}



	/* FILL IN THE METHODS BELOW. */

	/* Returns the index of the node to the left of the node at i. */
	private int getLeftOf(int i) {
		return i-1;
	}

	/* Returns the index of the node to the right of the node at i. */
	private int getRightOf(int i) {
		return i+1;
	}

	/* Returns the index of the node that is the parent of the node at i. */
	private int getParentOf(int i) {
		//YOUR CODE HERE
		if (i % 2 == 1) {
			i = (i - 1) / 2;
		}
		if (i % 2 == 0) {
			i = i / 2;
		}
		return i;
	}

	/* Adds the given node as a left child of the node at the given index. */
	private void setLeft(int index, Node n) {
		//YOUR CODE HERE
		setNode((2*index), n);
	}

	/* Adds the given node as the right child of the node at the given index. */
	private void setRight(int index, Node n) {
		//YOUR CODE HERE
		setNode((2*index + 1), n);
	}

	/** Returns the index of the node with smaller priority. Precondition: not
	  * both nodes are null. */
	private int min(int index1, int index2) {
		return min(index1,index2);
	}

	/* Returns the Node with the smallest priority value, but does not remove it
	 * from the heap. */
	public Node peek() {
		//YOUR CODE HERE
		return getNode(min(1,10));
	}

	/* Bubbles up the node currently at the given index. */
	private void bubbleUp(int index) {

	}

	/* Bubbles down the node currently at the given index. */
	private void bubbleDown(int index) {

	}

	/* Inserts an item with the given priority value. Same as enqueue, or offer. */
	public void insert(T item, double priority) {

	}

	/* Returns the Node with the smallest priority value, and removes it from
	 * the heap. Same as dequeue, or poll. */
	public Node removeMin() {

		return getNode(contents.size()/2);
	}

	/* Changes the node in this heap with the given item to have the given
	 * priority. You can assume the heap will not have two nodes with the same
	 * item. Check for item equality with .equals(), not == */
	public void changePriority(T item, double priority) {
		//YOUR CODE HERE
	}

}
