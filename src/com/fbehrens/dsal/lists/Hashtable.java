package com.fbehrens.dsal.lists;
import com.fbehrens.dsal.*;
/**
 * An iterator to traverse the elements of a hashtable.
 * 
 * @author Rossmanith
 *
 * @param <K> The type of keys.
 * @param <D> The type of the data.
 */
class HashIterator<K,D> implements Iterator<K,D> {
  /**
   * The current index, will be larger than size
   * if all elements have been traversed.
   */
  private int i;
  /**
   * The size of the table (i.e., the nr of slots, not
   * the number of elements that are stored in all lists
   * in the array).  
   */
  private int size;
  /**
   * The table containing the elements. 
   */
  private Array<List<K,D>> table;
  /**
   * The iterator of the current list.
   */
  private Iterator<K,D> l;

  /**
   * Constructs a new HashIterator from the internal table
   * and the tablesize.
   * @param t The table containing the elements to iterate over.
   * @param s The number of slots in the table t. 
   */
  public HashIterator(Array<List<K,D>> t, int s) {
    table = t;
    size = s;
    i=-1;
    l=null;   
    next_list_iterator();
  }

  /**
   * Jumps to the next list which has elements we
   * want to iterate over. If there is no list left,
   * then this.i >= this.size holds after calling
   * this method. 
   */
  void next_list_iterator() {
    while(++i<size)
      /*
       * if there is a list which has elements
       * then we have found the next list we have
       * to iterate over.
       */
      if(table.get(i)!=null && !table.get(i).isempty()) {
        l = table.get(i).iterator();
	break;
      }
  }

  public boolean more() {
    /*
     * just compare i with size, see docu of attribute i.
     */
    return i<size;
  }

  public void step() {
    /*
     * first perform step in list
     */
    l.step();
    /*
     * if one is at the end of the current list,
     * then go to next list
     */
    if(!l.more()) next_list_iterator();
  }

  public K key() {
    return l.key();
  }

  public D data() {
    return l.data();
  }
}

/**
 * A hashtable implementation of the Map-interface.
 * Conflicts are resolved by allowing multiple entries per
 * array element. Automatic reallocation of the table is 
 * performed if the table is too empty or too crowded.
 * Standard access methods like insertion, deletion, etc. all
 * have amortized cost O(1) if a good hash-function, i.e. {@link Object#hashCode()} is used.
 * 
 * @author Rossmanith
 *
 * @param <K> The type of the keys. Note that {@link Object#hashCode()} and {@link Object#equals(Object)} must
 *             conform to each other, i.e. whenever {@code o1.equals(o2) == true} then {@code o1.hashCode() == o2.hashCode()} must
 *             hold. Otherwise, this data-structure will have unexpected behaviour. 
 * @param <D> The type of the data.
 */
public class Hashtable<K,D> extends Dictionary<K,D> {

  /**
   * The internal array to store the hashtable.
   * For each position there is a list of 
   */
  Array<List<K,D>> table;
  /**
   * The current size of the table.
   */
  int tablesize; 
  /**
   * The current nr of elements in the table. 
   */
  int size=0;

  /**
   * Constructs an empty hashtable with initial tablesize s.
   * @param s The initial size of the table.
   */
  public Hashtable(int s) {
    table = new Array<List<K,D>>(tablesize=s);
  }

  /**
   * Constructs an empty hashtable with default initial tablesize (10).
   */
  public Hashtable() {
    table = new Array<List<K,D>>(tablesize=10);
  }


  public Iterator<K,D> iterator() {
    return new HashIterator<K,D>(table, tablesize);
  }

/**
 * Adjusts the table size if there are too few or to many elements
 * in the table.
 */
void rehash() {
  /*
   * first check, whether rehashing should be performed
   */
  if(size<=tablesize && 4*size+10>=tablesize) return;
  /*
   * if so, create a new array and copy all elements
   * of this table into the new array
   */
  int newtablesize = 2*size+10;
  Array<List<K,D>> newtable;
  newtable = new Array<List<K,D>>(newtablesize);
  Iterator<K,D> it;
  for(it=iterator(); it.more(); it.step()) {
    int l = it.key().hashCode() % newtablesize;
    if(l<0) l = -l;
    if(newtable.get(l)==null) newtable.set(l, new List<K,D>());
    newtable.get(l).insert(it.key(), it.data());
  }
  /*
   * finally update the reference of the internal table, 
   * and its size.
   */
  table = newtable; tablesize = newtablesize;
}

  /**
   * Computes the array-index on which to store the
   * data for the given key k.
   * @param k The key for that the index should be determined.
   * @return The index of the table at that data for key k should be stored.  
   */
  int slot(K k) {
    int n = k.hashCode() % tablesize;
    /*
     * As % may result in negative values, we have to take the absolute value.
     */
    return n > 0 ? n : -n;
  }
  
  /**
   * Associates the data d to the key k in this map.
   * A possible old entry for k will be deleted. 
   * (amortized cost of O(1) when using a good hash-function)
   * @see Map#insert(Object, Object) 
   * @param k The key to store.
   * @param d The data associated to the key k.
   */
  public void insert(K k, D d) {
    int l = slot(k);
    /* 
     * if there is no list, create a new one for that slot.
     */
    if(table.get(l)==null) table.set(l, new List<K,D>());
    /*
     * there only is a increase in size, if k is not already present
     */
    if(!table.get(l).iselement(k)) size++;
    /*
     * finally insert (or overwrite) the data for k.
     */
    table.get(l).insert(k, d);
    /*
     * and adjust table-size if needed
     */
    rehash(); 
  }

  /**
   * Removes the entry which stores the data for the key k.
   * Idle operations if there is no data stored for k.
   * (amortized cost of O(1) when using a good hash-function)
   * @param k The key to delete with its associated data.
   * @see Map#delete(Object) 
   */
  public void delete(K k) {
    int l = slot(k);
    /*
     * first check whether the k is present in this table.
     * Otherwise, we can return without deletion.
     */
    if(table.get(l)==null || !table.get(l).iselement(k)) return;
    /*
     * perform the deletion
     */
    table.get(l).delete(k);
    /*
     * check k was the only element of the list,
     * in that case the now empty list is replaced by null
     */
    if(table.get(l).isempty()) table.set(l, null);
    size--;
    /*
     * and adjust table-size if needed
     */
    rehash();
  }
      
  /**
   * Looks up the data stored under the key k.
   * (amortized cost of O(1) when using a good hash-function)
   * @param k The key under which the data is stored.
   * @return The data, which is stored under the key k, or null, if
   *  there is no data associated to k.
   * @see Map#find(Object)
   */
  public D find(K k) {
    int l = slot(k);
    /* 
     * just look for k in the list for key k. 
     */
    if(table.get(l)==null) return null;
    return table.get(l).find(k);
  }

  /**
   * Checks whether the key k is present in this map.
   * (amortized cost of O(1) when using a good hash-function)
   * @param k The key to look for.
   * @return true, if k is contained in this map, and false, otherwise.
   * @see Map#iselement(Object) 
   */
  public boolean iselement(K k) {
    int l = slot(k);
    /* 
     * just look for k in the list for key k. 
     */
    return table.get(l)!=null && table.get(l).iselement(k);
  }

  /**
   * Returns the size of this map, i.e. the number
   * of keys, under which data is stored in O(1).
   */
  public int size() {
    return size;
  }

}
