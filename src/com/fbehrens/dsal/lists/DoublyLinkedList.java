package com.fbehrens.dsal.lists;

import java.util.Iterator;

/**
 * A doubly linked list offers efficient implementations for accessing
 * the beginning AND the end of the list. Also, the predecessor and the
 * successor of any given node can be accessed in constant time.
 * The list achieves this by storing a link to not only the successor,
 * but also the predecessor in every node. If the list is implemented
 * with an empty head, this means that the head always has a link to
 * the first and the last element of the list. Implementing the list
 * with an empty head that is there even if the list is empty has more
 * advantages - deleting and inserting do not need to check for nulls
 * when accessing the head or inserting elements.
 * 
 * Runtimes:
 * 	- append/prepend/pop/dequeue: O(1)
 *  - contains/delete: O(n)
 *  - equals/toString: O(n)
 */
public class DoublyLinkedList<VALUE> implements AbstractList<VALUE>{
	
	private static class Node<VALUE> {
		public final VALUE value;
		private Node<VALUE> previous, next;
		
		public Node(VALUE value){
			this.value = value;
		}
		
		public void delete(){
			this.previous.next = this.next;
			this.next.previous = this.previous;
		}
		
		public void append(Node<VALUE> appendMe){
			appendMe.next = this.next;
			appendMe.previous = this;
			this.next.previous = appendMe;
			this.next = appendMe;
		}
	}
	
	private final Node<VALUE> head;
	private int size;
	
	public DoublyLinkedList(){
		this.head = new Node<VALUE>(null);
		this.head.next = this.head;
		this.head.previous = this.head;
		this.size = 0;
	}
	
	@Override
	public void append(VALUE value){
		Node<VALUE> appendMe = new Node<>(value);
		this.head.previous.append(appendMe);
		this.size++;
	}
	
	@Override
	public void prepend(VALUE value){
		Node<VALUE> prependMe = new Node<>(value);
		this.head.append(prependMe);
		this.size++;
	}
	
	@Override
	public void delete(VALUE valueToDelete){
		Node<VALUE> nodeToDelete = findFirstNodeWithValue(valueToDelete);
		if(nodeToDelete != null && nodeToDelete != this.head){
			nodeToDelete.delete();
			this.size--;
		}
	}
	
	@Override
	public boolean contains(VALUE toFind) {
		return findFirstNodeWithValue(toFind) != null;
	}
	
	@Override
	public VALUE firstElement(){
		return head.next.value;
	}
	
	@Override
	public VALUE lastElement(){
		return head.previous.value;
	}
	
	@Override
	public VALUE pop(){
		VALUE toReturn = head.next.value;
		if(head.next != head){
			size--;
		}
		head.next.delete();
		return toReturn;
	}
	
	@Override
	public VALUE dequeue(){
		VALUE toReturn = head.previous.value;
		if(head.previous != head){
			size--;
		}
		head.previous.delete();
		return toReturn;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	private Node<VALUE> findFirstNodeWithValue(VALUE toFind){
		Node<VALUE> currentlyAt = this.head.next;
		while(currentlyAt != this.head){
			if(toFind.equals(currentlyAt.value)){
				return currentlyAt;
			}
			currentlyAt = currentlyAt.next;
		}
		return null;
	}

	@Override
	public Iterator<VALUE> iterator() {
		// an iterator is required so that this object can be used in
		// for-each loops
		return new Iterator<VALUE>(){
			private Node<VALUE> currentlyAt = DoublyLinkedList.this.head.next;
			@Override
			public boolean hasNext(){
				return currentlyAt != DoublyLinkedList.this.head;
			}			
			@Override
			public VALUE next() {
				VALUE value = currentlyAt.value;
				currentlyAt = currentlyAt.next;
				return value;
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			DoublyLinkedList<VALUE> other = (DoublyLinkedList<VALUE>) obj;
			Iterator<VALUE> selfIter = this.iterator(), otherIter = other.iterator();
			while(selfIter.hasNext()){
				if(!otherIter.hasNext()){
					return false;
				}
				VALUE selfVal = selfIter.next(), otherVal = otherIter.next();
				
			}
			if(otherIter.hasNext()){
				return false;
			}
			return true;			
		} catch (ClassCastException e){
			return false;
		}
	}
	
	@Override
	public String toString() {
		String result = "[";
		for(VALUE v:this){
			result += v.toString() + ",";
		}
		if(result.endsWith(",")){
			result = result.substring(0, result.length()-1);
		}
		return result + "]";
	}
}
