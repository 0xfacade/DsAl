package com.fbehrens.dsal.elementare_datenstrukturen;

import java.util.Iterator;

/**
 * A doubly linked list uses nodes to store its values. Every node
 * holds a value and a link to the previous and next nodes.
 * This particular implementation always has a head node with a value of null,
 * even when the list is actually empty. A lot of special cases
 * do not have to be taken care of when accessing the head, therefore.
 * 
 * Runtimes:
 * 	- append/prepend: O(1)
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
	
	public DoublyLinkedList(){
		this.head = new Node<VALUE>(null);
		this.head.next = this.head;
		this.head.previous = this.head;
	}
	
	@Override
	public void append(VALUE value){
		Node<VALUE> appendMe = new Node<>(value);
		this.head.previous.append(appendMe);
	}
	
	/**
	 * Inserts the value at the beginning of the list.
	 * This method is special to {@link DoublyLinkedList}:
	 * usually, lists do not have this feature because
	 * it cannot be efficiently implemented.
	 */
	public void prepend(VALUE value){
		Node<VALUE> prependMe = new Node<>(value);
		this.head.append(prependMe);
	}
	
	@Override
	public void delete(VALUE valueToDelete){
		Node<VALUE> nodeToDelete = findFirstNodeWithValue(valueToDelete);
		if(nodeToDelete != null){
			nodeToDelete.delete();
		}
	}
	
	@Override
	public boolean contains(VALUE toFind) {
		return findFirstNodeWithValue(toFind) != null;
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
