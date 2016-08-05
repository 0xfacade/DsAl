package com.fbehrens.dsal;

/**
 * A SimpleIterator only iterates over the keys, but hides the data associated
 * to the keys.
 * 
 * @author Rossmanith
 *
 * @param <K>
 *            The type of the keys.
 */
public class SimpleIterator<K> {
	/*
	 * iterator is the hidden Iterator to access all the keys; in essence, all
	 * calls are passed to this internal iterator.
	 */
	private final Iterator<K, ?> iterator;

	/**
	 * Creates a new SimpleIterator from an {@link Iterator}.
	 * 
	 * @param i
	 *            The iterator that will provide the keys for the newly created
	 *            SimpleIterator.
	 */
	public SimpleIterator(Iterator<K, ?> i) {
		iterator = i;
	}

	/**
	 * The method is the same as in {@link Iterator#step()}.
	 */
	public void step() {
		iterator.step();
	}

	/**
	 * The method is the same as in {@link Iterator#more()}.
	 */
	public boolean more() {
		return iterator.more();
	}

	/**
	 * The method is the same as in {@link Iterator#key()}.
	 */
	public K key() {
		return iterator.key();
	}
}
