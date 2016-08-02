package com.fbehrens.dsal.elementare_datenstrukturen;

/**
 * All lists that we are going to compare will have to implement this interface
 * so we can use them interchangeably.
 * All lists are {@link java.lang.Iterable} for data access.
 *
 * @param <VALUE> type of elements the list holds
 */
public interface AbstractList<VALUE> extends Iterable<VALUE> {
	
	/**
	 * Appends the supplied value to the end of the list.
	 */
	public void append(VALUE value);
	
	/**
	 * Deletes one occurrence of the supplied value from the list.
	 */
	public void delete(VALUE value);
	
	/**
	 * Returns true if and only if the supplied value ist contained in the list.
	 */
	public boolean contains(VALUE value);
}
