package com.fbehrens.dsal.lists;

/**
 * A list-based implementation of a queue, i.e.
 * FIFO-buffer, where all methods need constant time.
 * 
 * @author Rossmanith
 *
 * @param <D> The key of the data to store in a queue.
 */
public class Queue<D> {
 /**
  * The underlying list which stores the elements.
  */
  private List<Object,D> queue;
  /**
   * The size of the queue is stored, 
   * as the list-implementation of {@link List#size()}
   * requires linear time.
   */
  private int size;
  /**
   * Create an empty queue.
   */
  public Queue() { queue = new List<Object,D>(); size = 0; }
  /**
   * Checks whether the queue is empty.
   * @return true, iff there are no elements stored in this queue.
   */  
  public boolean isempty() { return size==0; }
  /**
   * Returns and removes the element at the front
   * of this queue. 
   * Requires that this queue is non-empty.
   */
  public D dequeue() {
    D x = queue.lastnode().data;
    queue.lastnode().delete();
    size--;
    return x;
  }
  /**
   * Inserts the given element x at the end of this queue.
   * @param x The element to store in this queue.
   */
  public void enqueue(D x) { queue.prepend(null, x); size++; }
  /**
   * Returns the number of elements in this queue.
   */
  public int size() { return size; }
}
