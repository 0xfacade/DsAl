package com.fbehrens.dsal;

import com.fbehrens.dsal.arrays.Array;

/**
 * A Map is a data structure to store data under key
 * values, or just keys. For each key there can be at
 * most one datum that is associated to that key.
 * Maps provide methods to lookup data {@link #find(Object)},
 * to change the map {@link #insert(Object, Object)}, {@link #delete(Object)},
 * to iteratate over keys {@link #simpleiterator()} and
 * over both keys and data {@link #iterator()}. Moreover,
 * there are possibilities to display a map {@link #print()}
 * and to store a map in an array {@link #array()}.
 * 
 * 
 * @author Rossmanith
 *
 * @param <K> The type of the keys.
 * @param <D> The type of the data.
 */
public interface Map<K,D> {
    /**
     * Associates the data d to the key k in this map.
     * A possible old entry for k will be deleted. 
     * @param k The key to store.
     * @param d The data associated to the key k.
     */
    public void insert(K k, D d); 
    /**
     * Removes the entry which stores the data for the key k.
     * Idle operations if there is no data stored for k.
     * @param k The key to delete with its associated data.
     */
    public void delete(K k);
    /**
     * Looks up the data stored under the key k.
     * @param k The key under which the data is stored.
     * @return The data, which is stored under the key k, or null, if
     *  there is no data associated to k.
     */
    public D find(K k);
    /**
     * Checks whether the key k is present in this map.
     * @param k The key to look for.
     * @return true, if k is contained in this map, and false, otherwise. 
     */
    public boolean contains(K k);
    /**
     * Returns the size of this map, i.e. the number
     * of keys, under which data is stored.
     */
    public int size();
    /**
     * Checks, whether this map is empty, i.e., checks
     * size() == 0.
     */
    public boolean isempty();
    /**
     * Creates a new iterator over all associations
     * between keys and data.
     * @return The {@link Iterator} over all keys with
     *   associated data.
     */
    public Iterator<K,D> iterator();
    /**
     * Creates a new iterator only over the keys.
     * @return The {@link SimpleIterator} over all keys.
     */
    public SimpleIterator<K> simpleiterator();
    /**
     * Converts this map into an array of all keys.
     * @return A new array which contains all keys of this map.
     */
    public Array<K> array();
    /**
     * Prints the map to stdout
     */
    public void print();
}
