package com.fbehrens.dsal.lists;
import com.fbehrens.dsal.*;
/**
 * An Listiterator allows all operations of an {@link Iterator}, and
 * additionally has operations to insert and delete entries at the
 * current position.

 * @author Rossmanith
 *
 * @param <K> The type of the keys.
 * @param <D> The type of the data.
 * @see Iterator
 * @see List#iterator()
 * @see Map#iterator()
 */
public interface Listiterator<K,D> extends Iterator<K,D> {
    /**
     * Deletes the current entry (and directly steps to the next entry)
     */
    public void delete();
    
    /**
     * Inserts a new entry (k,d) behind the current entry.
     * @param k
     * @param d
     */
    public void append(K k, D d);
    
    /**
     * Inserts a new entry (k,d) before the current entry.
     * @param k
     * @param d
     */
    public void prepend(K k, D d);
}