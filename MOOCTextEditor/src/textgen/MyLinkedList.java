package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size=0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next  = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		if(element ==null) {
			throw new NullPointerException("Null");
		}
		LLNode<E> newNode = new LLNode<E>(element,tail.prev,tail);
		tail.prev.next = newNode;
		tail.prev = newNode;
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public LLNode getNode(int index) {		
		int currIndex = 0;
		LLNode currNode = head.next;
		while(currIndex<=index) {
			if(currIndex==index) {
				break;
			}
			else {
				currIndex++;
				currNode = currNode.next;
			}
		}
		return currNode;
	}
	
	public E get(int index) 
	{
		// TODO: Implement this method.
		if(this.size<=index||index<0) {
			throw new IndexOutOfBoundsException("Invalid Index");
		}
		LLNode<E>currNode = getNode(index);
		E currData = (E)currNode.data;
		
		return currData;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if(element ==null) {
			throw new NullPointerException("Null");
		}
		
		
		LLNode<E> currNode = getNode(index);
		LLNode<E> newNode = new LLNode<E>(element, currNode.prev,currNode);
		currNode.prev.next = newNode;
		currNode.prev = newNode;
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if(index<0||index>=size) {
			throw new IndexOutOfBoundsException("Invalid Index");
		}
		
		LLNode<E> currNode = getNode(index);
		E removedData = currNode.data;
		currNode.prev.next = currNode.next;
		currNode.next.prev = currNode.prev;
		size--;
		return removedData;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		//해당 인덱스에 위치한 데이터를 새로운 데이터로 교체
		if(element ==null) {
			throw new NullPointerException("Null");
		}
		if(index<0||index>=size) {
			throw new IndexOutOfBoundsException("Invalid Index");
		}
		
		LLNode currData = getNode(index);
		E oldData = get(index);
		
		currData.data = element;		
		return oldData;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode prev, LLNode next) {
		this.data = e;
		this.prev = prev;
		this.next = next;
	}

}
