package com.fbehrens.dsal;

import com.fbehrens.dsal.arrays.Array;

/**
 * A dictionary provides some default implementations of methods that are
 * required for the {@link Map}-interface. Only {@link Map#insert(Object, Object)},
 * {@link Map#delete(Object)}, and {@link Map#iterator()} have to be implemented
 * for subclasses of Dictionary, however, other methods may also be implemented 
 * to improve efficiency.
 * 
 * @author Rossmanith
 *
 * @param <K> The type of the keys.
 * @param <D> The type of the data.
 */
public abstract class Dictionary<K,D> implements Map<K,D> {
  public abstract void insert(K k, D d); 
  public abstract void delete(K k); 
  public abstract Iterator<K,D> iterator();
  /**
   * Linear time implementation of {@link Map#find(Object)}.
   */
  public D find(K k) {
      Iterator<K,D> it;
      for(it=iterator(); it.more(); it.step()) {
          if (it.key().equals(k)) {
              return it.data();
          }
      }
      return null;
  }
  /**
   * Linear time implementation of {@link Map#contains(Object)}.
   */
  public boolean contains(K k) {
      Iterator<K,D> it;
      for(it=iterator(); it.more(); it.step()) {
          if (it.key().equals(k)) {
              return true;
          }
      }
      return false;      
  }
  /**
   * Constant time implementation of {@link Map#isempty()} provided
   * that creation of an {@link Iterator} with {@link #iterator()} costs only constant time.
   */
  public boolean isempty() {
    return !iterator().more();
  }

  /**
   * Linear time implementation of {@link Map#size()}
   */
  public int size() {
    Iterator<K,D> it;
    int size = 0;
    for(it=iterator(); it.more(); it.step()) size++;
    return size;
  }

  public SimpleIterator<K> simpleiterator() {
    return new SimpleIterator<K>(iterator());
  }

  public Array<K> array() {
    int i;
    Array<K> a = new Array<K>(size());
    SimpleIterator<K> it;
    for(i=0, it=simpleiterator(); it.more(); i++, it.step()) 
      a.set(i, it.key());
    return a;
  }

  public void print() {
    Iterator<K,D> i;
    for(i=iterator(); i.more(); i.step())
    	System.out.println(i.key()+" : "+i.data());
  }

  
}
