package com.fbehrens.dsal.lists;
import com.fbehrens.dsal.*;
/**
 * A listnode stores a key of type K, a datum of type D, and 
 * links to a previous and a succeeding listnode.
 * @param <K>
 * @param <D>
 */
class Listnode<K,D> {
    K key; 
    D data;
    Listnode<K,D> pred, succ;
    Listnode(K k, D d) {
        key = k; data = d; pred = null; succ = null; }
    /**
     * Deletes this listnode from a list
     */
    void delete() {
        pred.succ = succ; succ.pred = pred; }
  
    /**
     * Copies key and datum of the listnode n into this listnode.
     * @param n The listnode to get the key and datum.
     */
    void copy(Listnode<K,D> n) {
        key = n.key; data = n.data; }
    
    /**
     * Inserts newnode between this node and the successor of this node.
     * Note that the links of newnode will be ignored and overwritten. 
     * @param newnode
     */
    void append(Listnode<K,D> newnode) {
        newnode.succ = succ; newnode.pred = this;
        succ.pred = newnode; succ = newnode; }
}

/**
 * A List is a {@link Map} implemented as double-linked list.
 * There are linear time insertion and lookup operations. 
 * Moreover, there are constant time operations to add an element 
 * in front of the list or at the end of the list. However, if these
 * constant time operations are used, 
 * then the list is not a {@link Map} any more, e.g., there
 * might be multiple data-entries for the same key. 
 * <BR>
 * Note, that {@code null}-values
 * are not supported as keys. 
 * 
 * 
 * @author Rossmanith
 *
 * @param <K> The type of the keys
 * @param <D> The type of the data
 */
/*
 * In the implementation a dummy head element is used to avoid
 * special cases when handling empty lists, inserting at the front, etc.
 * Moreover, the list is cyclic, i.e., from the dummy head element there
 * is a back link to the last element, and from the last element there
 * is a forward link to the dummy head element. 
 */
public class List<K,D> extends Dictionary<K,D> {
    Listnode<K,D> head;
    public List() {
        head = new Listnode<K,D>(null, null);
        head.pred = head.succ = head;
    }

    public void insert(K k, D d) {
        Listnode<K,D> n = findnode(k);
        if(n!=null) n.copy(new Listnode<K,D>(k, d));
        else head.append(new Listnode<K,D>(k, d));
    }

    /**
     * Checks whether the key k is present in this map. (linear time)
     * @param k The key to look for.
     * @return true, if k is contained in this map, and false, otherwise.
     * @see Map#iselement(Object) 
     */
    public boolean iselement(K k) {
        return findnode(k) != null;
    }

    /**
     * Appends the value k -> d at the end of the list. (constant time)
     * Note that this operation can destroy uniqueness of the
     * key-to-data mapping as required in {@link Map}.
     * @param k
     * @param d
     */
    public void append(K k, D d) {
        head.pred.append(new Listnode<K,D>(k, d));
    }

    /**
     * Appends the value k -> d at the beginning of the list. (constant time)
     * Note that this operation can destroy uniqueness of the
     * key-to-data mapping as required in {@link Map}.
     * @param k
     * @param d
     */
    public void prepend(K k, D d) {
        head.append(new Listnode<K,D>(k, d));
    }

    /**
     * Returns the first Listnode of this list.
     * May only be called if the list is non-empty.
     */
    protected Listnode<K,D> firstnode() {
        return head.succ; 
    }

    /**
     * Returns the last Listnode of this list.
     * May only be called if the list is non-empty.
     */
    protected Listnode<K,D> lastnode() {
        return head.pred;
    }

    /**
     * Computes the (first) {@link Listnode} with given key k.
     * @param k The key to search for.
     * @return null, if k is not present in this list, and the (first)
     * {@link Listnode} with key k of this list, otherwise.
     */
    protected Listnode<K,D> findnode(K k) {
        Listnode<K,D> n;
        head.key = k;
        for(n=head.succ; !n.key.equals(k); n=n.succ) ;
        head.key = null;
        if(n==head) return null;
        return n;
    }

    /**
     * Looks up the data stored under the key k. (linear time)
     * @param k The key under which the data is stored.
     * @return The data, which is stored under the key k, or null, if
     *  there is no data associated to k.
     * @see Map#find(Object)
     */
    public D find(K k) {
        Listnode<K,D> n = findnode(k);
        if(n==null) return null;
        return n.data;
    }

    /**
     * Deletes the (first) element of this list with given key k, if such
     * an element exists.
     * @param k The key which should be deleted.
     */
    public void delete(K k) {
        Listnode<K,D> n = findnode(k);
        if(n!=null) n.delete();
    }

    public Listiterator<K,D> iterator() {
        return new ListIterator();
    }


    /**
     * A ListIterator just stores the position of the current listnode. 
     */
    private class ListIterator implements Listiterator<K,D> {
        Listnode<K,D> n; // our current position 
        public ListIterator() {
            n = List.this.head.succ;
        }
        public boolean more() { return n!=List.this.head; }
        public K key() { return n.key; }
        public D data() { return n.data; }
        Listnode<K,D> node() { return n; }
        public void step() { n = n.succ; }
        public void delete() {
            n.delete();
            n = n.succ;
        }
        public void append(K k, D d) { n.append(new Listnode<K, D>(k, d)); }    
        public void prepend(K k, D d) { n.pred.append(new Listnode<K, D>(k, d)); }
    }

}