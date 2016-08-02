package com.fbehrens.dsal.elementare_datenstrukturen;

/**
 * All lists that we are going to compare will have to implement this interface
 * so we can use them interchangeably.
 * All lists are {@link java.lang.Iterable} for data access.
 * 
 * IMPORTANT: not all lists support all operations efficiently! We do implement them
 * anyway so we can compare their runtimes and see what difference it makes.
 *
 * @param <VALUE> type of elements the list holds
 */
public interface AbstractList<VALUE> extends Iterable<VALUE> {
	
	/**
	 * Appends the supplied value to the end of the list.
	 */
	public void append(VALUE value);
	
	/**
	 * Prepends the supplied value at the beginning of the list.
	 */
	public void prepend(VALUE value);
	
	/**
	 * Deletes one occurrence of the supplied value from the list.
	 */
	public void delete(VALUE value);
	
	/**
	 * Returns true if and only if the supplied value ist contained in the list.
	 */
	public boolean contains(VALUE value);
	
	/**
	 * Retrieves the first element of the list.
	 */
	public VALUE firstElement();
	
	/**
	 * Retrieves the last element of the list.
	 */
	public VALUE lastElement();
	
	/**
	 * Removes the first element from the list and returns it.
	 */
	public VALUE pop();
	
	/**
	 * Removes the last element from the list and returns it.
	 */
	public VALUE dequeue();
}
