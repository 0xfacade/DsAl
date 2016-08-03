package com.fbehrens.dsal.lists;

import java.util.Iterator;

public class SimpleLinkedList<VALUE> implements AbstractList<VALUE> {
	
	private static class Node<VALUE> {
		public final VALUE value;
		public Node<VALUE> next;
		public Node(VALUE value){
			this.value = value;
		}
	}
	
	private final Node<VALUE> head;
	private int size;
	
	public SimpleLinkedList(){
		this.head = new Node<VALUE>(null);
		this.size = 0;
	}

	@Override
	public Iterator<VALUE> iterator() {
		return new Iterator<VALUE>() {
			
			private Node<VALUE> currentlyAt = SimpleLinkedList.this.head.next;
			@Override
			public boolean hasNext() {
				return currentlyAt != null;
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
	public void append(VALUE value) {
		Node<VALUE> lastNode = this.head;
		while(lastNode.next != null){
			lastNode = lastNode.next;
		}
		lastNode.next = new Node(value);
		this.size++;
	}

	@Override
	public void prepend(VALUE value) {
		Node<VALUE> newNode = new Node(value);
		newNode.next = head.next;
		head.next = newNode;
		this.size++;
	}

	@Override
	public void delete(VALUE value) {
		Node<VALUE> nodeBefore = findNodeJustBeforeValue(value);
		if(nodeBefore != null){
			nodeBefore.next = nodeBefore.next.next;
			this.size--;
		}
	}

	@Override
	public boolean contains(VALUE value) {
		return findNodeJustBeforeValue(value) != null;
	}
	
	private Node<VALUE> findNodeJustBeforeValue(VALUE value){
		Node<VALUE> prev = this.head, current = prev.next;
		while(current != null){
			if((current.value == null && value == null)
				|| (value != null && (value.equals(current.value)))){
				return prev;
			} else {
				prev = current;
				current = current.next;
			}
		}
		return null;
	}

	@Override
	public VALUE firstElement() {
		if(head.next != null){
			return head.next.value;
		} else {
			return null;
		}
	}

	@Override
	public VALUE lastElement() {
		Node<VALUE> lastNode = head;
		while(lastNode.next != null){
			lastNode = lastNode.next;
		}
		return lastNode.value;
	}

	@Override
	public VALUE pop() {
		if(head.next != null){
			VALUE toReturn = head.next.value;
			head.next = head.next.next;
			this.size--;
			return toReturn;
		} else {
			return null;
		}
	}

	@Override
	public VALUE dequeue() {
		if(head.next != null){
			Node<VALUE> prevLast = this.head, last = this.head.next;
			while(last.next != null){
				prevLast = last;
				last = last.next;
			}
			prevLast.next = null;
			this.size--;
			return last.value;
		} else {
			return null;
		}
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			SimpleLinkedList<VALUE> other = (SimpleLinkedList<VALUE>) obj;
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
