package com.fbehrens.dsal;
/**
 * An iterator allows to iterate over all entries of a {@link Map}.
 * E.g., the following code prints all entries of a {@link Map} to stdout.
 * <pre> 
 *  Iterator<K,D> i = someMap.iterator();
 *  while (i.more()) {
 *     System.out.println(i.key() + " -> " + i.data());
 *     i.step();
 *  } 
 *  
 *  // or equivalently,
 *  
 *  for (i=someMap.iterator(); i.more(); i.step()) {
 *      System.out.println(i.key() + " -> " + i.data());
 *  }    
 * </pre>
 * @author Rossmanith
 *
 * @param <K> The type of the keys.
 * @param <D> The type of the data.
 * @see Map
 * @see Map#iterator()
 */
public interface Iterator<K,D> {
    /**
     * Move to the next entry.
     */
    public void step();
    /**
     * Test whether the current entry is valid.
     */
    public boolean more();
    /**
     * Returns the key of the current entry, if
     * it is valid, cf. {@link #more()}.
     */
    public K key();
    /**
     * Returns the data of the current entry, if
     * it is valid, cf. {@link #more()}.
     */
    public D data();
}

