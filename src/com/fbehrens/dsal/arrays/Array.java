package com.fbehrens.dsal.arrays;

import java.util.Random;

import com.fbehrens.dsal.Iterator;
import com.fbehrens.dsal.SimpleIterator;

/**
 * An array is more convenient class of the built-in arrays. It can be used with
 * generic data of type D, and it is size adjusting.
 * 
 * @author Rossmanith
 *
 * @param <D>
 *            The type of the data stored in an array.
 */
public class Array<D> {
	/*
	 * The internal array to store this array.
	 */
	protected D[] a;

	/*
	 * size-1 is the largest index for which a datum is stored in this array, or
	 * equivalently, size is the number of elements that are stored in this
	 * array (altough not all elements must have been stored, e.g. new
	 * Array().set(5) has size 6.)
	 */
	protected int size;

	/**
	 * reallocated new space if needed
	 */
	@SuppressWarnings("unchecked")
	private void realloc() {
		if (size >= a.length) {
			D[] b = (D[]) new Object[2 * size];
			for (int i = 0; i < a.length; i++)
				if (a[i] != null)
					b[i] = a[i];
			a = b;
		}
	}

	// built in arraycopy not self-contained, so let's dump it

	/**
	 * Returns the size of this array (constant time)
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks whether this array is empty (constant time)
	 */
	public boolean isempty() {
		return size == 0;
	}

	/**
	 * Ensures that after this call this array can access all elements with
	 * index i &le; s in constant time and sets the size to s.
	 * 
	 * @param s
	 *            Resize the array to have at least capacity s, if the current
	 *            capacity is not high enough.
	 */
	public void resize(int s) {
		get(s);
		size = s;
		realloc();
	}

	/**
	 * Sets the i-th element of this array to d. (Constant time, if the capacity
	 * is high enough, or linear time, otherwise)
	 * 
	 * @param i
	 *            The index of element that should be returned, must be
	 *            non-negative.
	 * @param d
	 *            The datum that should be stored at the i-th position of this
	 *            array.
	 */
	public void set(int i, D d) {
		if (i >= size) {
			size = i + 1;
			realloc();
		}
		a[i] = d;
	}

	/**
	 * Gets the i-th element of this array (in constant time).
	 * 
	 * @param i
	 *            The index of element that should be returned, must be
	 *            non-negative.
	 */
	public D get(int i) {
		if (i >= size)
			return null;
		return a[i];
	}

	/**
	 * Creates an empty array with capacity s.
	 * 
	 * @param s
	 *            The initial capacity of the array, must be non-negative.
	 */
	@SuppressWarnings("unchecked")
	public Array(int s) {
		size = 0;
		a = (D[]) new Object[s];
	}

	/**
	 * Creates an empty array with default capacity.
	 */
	@SuppressWarnings("unchecked")
	public Array() {
		size = 0;
		a = (D[]) new Object[10];
	}

	/**
	 * Permutes this array randomly using the given random number generator r.
	 * 
	 * @param r
	 *            The random number generator.
	 */
	public void permute_randomly(Random r) {
		for (int i = size - 1; i > 0; i--) {
			int j = r.nextInt(i + 1);
			D t = a[i];
			a[i] = a[j];
			a[j] = t;
		}
	}

	/**
	 * Prints this array to stdout.
	 */
	public void print() {
		for (int i = 0; i < size; i++)
			System.out.print("" + get(i) + " ");
		System.out.println();
	}

	/**
	 * Delivers an iterator over the objects stored in this array.
	 */
	public SimpleIterator<D> iterator() {
		return new SimpleIterator<D>(new Iterator<D, Object>() {
			private int i = 0;

			public Object data() {
				return null;
			}

			public D key() {
				return Array.this.a[i];
			}

			public boolean more() {
				return i < Array.this.size;
			}

			public void step() {
				i++;
			}
		});

	}
}
