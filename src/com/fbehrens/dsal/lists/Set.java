package com.fbehrens.dsal.lists;
import com.fbehrens.dsal.*;
import com.fbehrens.dsal.arrays.*;
/**
 * A set is like a {@link Map} without data, i.e.
 * it is a collection of elements without duplicates.
 * (Where {@link Object#equals(Object)} is used to compare elements).
 * 
 * Internally, the keys of a Map are used to store
 * the elements of the set. Note that it depends on
 * the Map whether null is supported as element.
 *  
 * @author Rossmanith
 *
 * @param <K> The type of the elements in the set.
 */
public class Set<K> {
  private final Map<K,?> h;
  /**
   * Creates a new empty set. 
   * (Here a {@link Hashtable} is used to store the elements)
   */
  public Set() {
    h = new Hashtable<K,Integer>();
  }
  /**
   * Creates a set where the elements are the keys of
   * the given map. Note that changes to this set will
   * have impact on the map m and vice versa!
   * @param m The map which defines this set.
   */
  public Set(Map<K,?> m) { h=m; }
  /**
   * Inserts an element k into the set.
   * @param k The element to insert.
   */
  public void insert(K k) { h.insert(k, null); }
  /**
   * Deletes an element k from the set if it is present in the set.
   * @param k The element to delete.
   */
  public void delete(K k) { h.delete(k); }
  /**
   * Checks whether the given element is in the set.
   * @param k The element to check.
   * @return true, if k is in the set, false, otherwise.
   */
  public boolean iselement(K k) { return h.contains(k); }
  /**
   * Gives an iterator over all elements in the set.
   */
  public SimpleIterator<K> iterator() {
  	return h.simpleiterator(); }
  /**
   * Creates a new array which stores all elements of this set.
   * @return An array containing exactly the elements of this set.
   */
  public Array<K> array() { return h.array(); }
}
