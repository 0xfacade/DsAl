package com.fbehrens.dsal.arrays;

import com.fbehrens.dsal.Pair;
import com.fbehrens.dsal.lists.Stack;

/**
 * An (O)rdered array is like an array but there additional methods to sort an
 * array ({@link #quicksort()} and {@link #heapsort()}). Moreover, for a sorted
 * array there are efficient methods to check whether an element is present in
 * an array {@link #binsearch(Comparable)}. Finally, there are methods like
 * {@link #insert(Comparable)} and {@link #extract_min()} which should only be
 * used if the array has a heap-structure and which ensure that the
 * heap-structure is guaranteed afterwards.
 * 
 * @author Rossmanith
 *
 * @param <D>
 *            The type of the elements in the array. These elements must be
 *            comparable.
 * @see Comparable
 */
public class OArray<D extends Comparable<D>> extends Array<D> {
	/**
	 * Sorts this array in place in O(n log(n)) time using the heap-sort
	 * algorithm.
	 */
	public void heapsort() {
		int s = size();
		/*
		 * first construct a heap from this array in O(n log(n))
		 */
		for (int i = 0; i < s; i++) {

			bubble_up(i);
		}
		/*
		 * second perform the sorting in O(n log(n))
		 */
		this.reverse_sort();
		/*
		 * and flip the array in O(n)
		 */
		for (int i = 0; i < s / 2; i++)
			swap(i, s - 1 - i);
	}

	/**
	 * Under the condition that the array is a heap, this method sorts the array
	 * in reverse order.
	 */
	private void reverse_sort() {
		for (int i = size() - 1; i > 0; i--) {
			swap(0, i);

			bubble_down(0, i);
		}

	}

	/**
	 * Compares the elements at position i and j in the array.
	 * 
	 * @param i
	 *            Valid range is 0 &le; i < size.
	 * @param j
	 *            Valid range is 0 &le; j < size.
	 * @return true if a[i] < a[j], false, otherwise.
	 */
	private boolean less(int i, int j) {
		return get(i).compareTo(get(j)) < 0;
	}

	/**
	 * Swaps the elements at positions i and j.
	 * 
	 * @param i
	 *            Valid range is 0 &le; i < size.
	 * @param j
	 *            Valid range is 0 &le; j < size.
	 */
	private void swap(int i, int j) {
		D temp = get(i);
		set(i, get(j));
		set(j, temp);
	}

	/**
	 * Bubbles up the element at position i.
	 * 
	 * @param i
	 *            The index of the element that should be bubbled up. Valid
	 *            range is 0 &le; i < size.
	 * @return The index at which the element at position i is stored after the
	 *         bubbling.
	 */
	private int bubble_up(int i) {
		while (i > 0 && less(i, (i - 1) / 2)) {
			swap((i - 1) / 2, i);
			i = (i - 1) / 2;
		}
		return i;
	}

	/**
	 * Bubbles down the element at position i. Assumes that i is the only index
	 * which is conflicting to the heap-property (i may be larger but not
	 * smaller than its children) and establishes the heap-property.
	 * 
	 * @param i
	 *            The index of the element that should be bubbled down. Valid
	 *            range is 0 &le; i < size().
	 * @return The index at which the element at position i is stored after the
	 *         bubbling.
	 */
	int bubble_down(int i) {
		return this.bubble_down(i, this.size());
	}

	/**
	 * Bubbles down the element at position i, assuming that the array has size
	 * s.
	 * 
	 * @param i
	 *            The index of the element that should be bubbled down. Valid
	 *            range is 0 &le; i < s.
	 * @param s
	 *            The current size of the (logical) array. Must not exceed
	 *            {@link Array#size()}.
	 * @return The index at which the element at position i is stored after the
	 *         bubbling.
	 */
	private int bubble_down(int i, int s) {
		int j;
		while (true) {
			if (2 * i + 2 >= s || less(2 * i + 1, 2 * i + 2))
				j = 2 * i + 1;
			else
				j = 2 * i + 2;
			if (j >= s || less(i, j))
				break;
			swap(i, j);
			i = j;
		}
		return i;
	}

	/**
	 * Inserts d into this array and preserves the heap-property if this array
	 * had the heap-property before the call to this method.
	 * 
	 * @param d
	 *            The data to insert.
	 */
	public void insert(D d) {
		// insert the element
		int pos = this.size();
		this.set(pos, d);
		// and preserve heap by bubbling
		this.bubble_up(pos);
	}

	/**
	 * Provided that this array has the heap-property it returns and removes the
	 * smallest element from the array. Afterwards this array still has the
	 * heap-property. Requires that there is at least one element in the array.
	 * 
	 * @return The minimal element of this array.
	 */
	D extract_min() {
		D m = get(0);
		swap(0, size() - 1);
		resize(size() - 1);
		bubble_down(0, this.size());
		return m;
	}

	/**
	 * Sorts this array using the quicksort-algorithm. (with runtimes O(n
	 * log(n)) in the average, and O(n<sup>2</sup>) in the worst case. The worst
	 * case occurs for example when invoking quicksort on an already sorted
	 * array.)
	 */
	public void quicksort() {
		/*
		 * the stack is the todo list, i.e. for each entry (l,r) in the stack we
		 * have to sort array between positions l to r.
		 */
		Stack<Pair<Integer, Integer>> stack = new Stack<Pair<Integer, Integer>>();
		stack.push(new Pair<Integer, Integer>(0, size - 1));
		/*
		 * first move smallest element to the beginning of the array. works as
		 * sentinal.
		 */
		for (int i = 1; i < size; i++)
			if (get(0).compareTo(get(i)) > 0) {
				D t = get(0);
				set(0, get(i));
				set(i, t);

			}
		while (!stack.isempty()) {
			Pair<Integer, Integer> p = stack.pop();
			int l = p.first(), r = p.second();
			/*
			 * i walks from the left to the middle j walks from the right to the
			 * middle
			 */
			int i = l - 1, j = r;
			/*
			 * choose pivot element (here: element at right bound)
			 */
			D t, pivot = get(j);
			do {
				/*
				 * walk to next positions i and j where i and j need to be
				 * swapped (i cannot get larger as r) (j cannot get smaller as
				 * l, as we first moved the smallest element to the beginning of
				 * the array) (note that in the end - if i and j cross - one
				 * additional swap is performed which has to be undone)
				 */
				do {
					i++;
				} while (get(i).compareTo(pivot) < 0);
				do {
					j--;
				} while (get(j).compareTo(pivot) > 0);
				/*
				 * and do the swap
				 */
				t = get(i);
				set(i, get(j));
				set(j, t);

			} while (i < j);
			/*
			 * undo additional swap step and swap pivot into the middle.
			 */
			set(j, get(i));
			set(i, get(r));
			set(r, t);

			/*
			 * sort left and right subarrays of pivot position if these arrays
			 * have at least size 2
			 */
			if (r - i > 1)
				stack.push(new Pair<Integer, Integer>(i + 1, r));
			if (i - l > 1)
				stack.push(new Pair<Integer, Integer>(l, i - 1));
		}

	}

	/**
	 * Performs a binary search of d, requires logarithmic time in the size of
	 * the array. Requires that this array is sorted.
	 * 
	 * @param d
	 *            The data to search for
	 * @return true iff d is contained in this array.
	 */
	public boolean binsearch(D d) {
		int l = 0, r = size - 1, m, c;
		while (l <= r) {
			m = (l + r) / 2;
			c = d.compareTo(get(m));
			if (c == 0)
				return true;
			if (c < 0)
				r = m - 1;
			else
				l = m + 1;
		}
		return false;
	}

}
