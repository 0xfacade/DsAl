package com.fbehrens.dsal.lists;
import com.fbehrens.dsal.*;
/** 
 * A list-based implementation of a stack (LIFO-buffer)
 * with only constant time operations.
 * 
 * @author Rossmanith
 *
 * @param <D> The type of the data to store in the stack.
 */
public class Stack<D> {
  /**
   * The underlying list which stores the elements.
   */
  private List<Object,D> stack;
  /**
   * The size of the stack is stored, 
   * as the list-implementation of {@link List#size()}
   * requires linear time.
   */
  private int size;
  /**
   * Creates an empty stack.
   */
  public Stack() { stack = new List<Object,D>(); size = 0; }
  /**
   * Checks whether this stack is empty.
   * @return true, iff there are no elements stored in this stack.
   */
  public boolean isempty() { return size==0; }
  /**
   * Returns and removes the top-most element of this stack.
   * May only be called if this stack is non-empty. 
   */
  public D pop() {
    D x = stack.firstnode().data;
    stack.firstnode().delete();
    size--;
    return x;
  }
  /**
   * Pushes the given element x on the top of this stack.
   * @param x The element to store in this stack.
   */
  public void push(D x) { stack.prepend(null, x); size++; }
  /**
   * Returns the number of elements in this stack.
   */
  public int size() { return size; }
}
