package com.fbehrens.dsal.trees;

import java.util.Random;

import com.fbehrens.dsal.Dictionary;
import com.fbehrens.dsal.Iterator;
import com.fbehrens.dsal.Map;
import com.fbehrens.dsal.arrays.Array;

/**
 * This class implements the {@link Map}-interface as skiplists. In a skiplist
 * each key-value element is inserted into the n lowest lists. Here, n is
 * determined randomly in a geometric distribution. So, given a propability p
 * with 0 &lt; p &lt; 1 a new key-value element is inserted into n lists with
 * propability p * (1-p)<sup>n-1</sup>.
 * 
 * All standard operations like insertion, deletion, finding, etc. need O(log n)
 * time in average.
 * 
 * @author Rossmanith
 *
 * @param <K>
 *            The type of the key. Must be comparable.
 * @param <D>
 *            The type of the data.
 * @see Comparable
 */
public class Skiplist<K extends Comparable<K>, D> extends Dictionary<K, D> {
	/**
	 * The size of the list, i.e., the number of keys.
	 */
	int size;
	/**
	 * This attribute stores the propability p as mentioned in the destriction
	 * of the {@link Skiplist}-class.
	 */
	double prob = 0.5;
	/**
	 * The random nr generator.
	 */
	Random rand;

	/**
	 * The class for the inner nodes of a skiplist. Just stores the key, the
	 * data, and the an array with the successors of that node.
	 */
	/*
	 * Note that only the succ-array of a skiplist-head will change its size,
	 * but the array of all other nodes will keep its size.
	 */
	class Node {
		Array<Node> succ;
		K key;
		D data;
	}

	/*
	 * Head and tail of the skiplist, won't be changed.
	 */
	Node head, tail;

	/**
	 * Creates a new empty skiplist with default geometric distribution (where p
	 * = 0.5).
	 */
	public Skiplist() {
		/*
		 * create head and tail and add one link on level 0 from head to tail
		 */
		head = new Node();
		tail = new Node();
		head.succ = new Array<Node>();
		tail.succ = new Array<Node>();
		head.succ.set(0, tail);
		size = 0;
		rand = new Random();
	}

	/**
	 * Creates a new skiplist with given random seed.
	 * 
	 * @param r
	 *            The seed that is used to initialize the random number
	 *            generator for the created skiplist.
	 */
	public Skiplist(int r) {
		/*
		 * first create the list with this() and then overwrite the random
		 * generator.
		 */
		this();
		rand = new Random(r);
	}

	/**
	 * Creates a new skiplist with given random seed and given probability p.
	 * 
	 * @param r
	 *            The seed that is used to initialize the random number
	 *            generator for the created skiplist.
	 * @param p
	 *            The probability p as mentioned in the desription of the
	 *            {@link Skiplist}-class.
	 */
	public Skiplist(int r, double p) {
		/*
		 * first create the list with this() and then overwrite the random
		 * generator and the probability value.
		 */
		this();
		rand = new Random(r);
		prob = p;
	}

	/**
	 * Inserts a the link to the new skiplist node n on the i-th level.
	 * 
	 * @param i
	 *            The level to insert the node.
	 * @param m
	 *            A node at which one can start to search for the right position
	 *            for the new node when inserting on the i-th level.
	 * @param n
	 *            The node to insert. Note that the key of n must not be present
	 *            at the i-th level before.
	 * @return A node at which one can start to search for the right position
	 *         for the new node on the (i-1)-th level.
	 */
	Node insert_on_level(int i, Node m, Node n) {
		/*
		 * Step forwards with m on the i-th level, unless the successor of m is
		 * the tail or the successor of m has a higher key than the key of n.
		 */
		while (m.succ.get(i) != tail && m.succ.get(i).key.compareTo(n.key) < 0)
			m = m.succ.get(i);
		/*
		 * Then insert n right after m on the i-th level.
		 */
		n.succ.set(i, m.succ.get(i));
		m.succ.set(i, n);
		return m;
	}

	/**
	 * Associates the data d to the key k in this skiplist. Takes O(log n) time
	 * in average.
	 * 
	 * @param k
	 *            The key to store.
	 * @param d
	 *            The data associated to the key k.
	 * @see Map#insert(Object, Object)
	 */
	public void insert(K k, D d) {
		/*
		 * first delete the old entry, if it exists
		 */
		delete(k);
		/*
		 * then generate the nr of levels s on which the new node should be
		 * inserted
		 */
		int s = 1;
		while (rand.nextDouble() >= prob)
			s++;
		/*
		 * and create the new node
		 */
		Node n = new Node();
		n.key = k;
		n.data = d;
		n.succ = new Array<Node>(s);
		/*
		 * now adjust the nr of successors of the head such the head has at
		 * least s successors. This is done by creating new links to the tail on
		 * the missing upper levels.
		 */
		Node m = head;
		for (int i = 0; i < s; i++)
			if (i >= head.succ.size())
				head.succ.set(i, tail);
		/*
		 * finally insert the new node on the lower s levels, starting from the
		 * highest.
		 */
		for (int i = s - 1; i >= 0; i--)
			m = insert_on_level(i, m, n);
		/*
		 * and finally update the size
		 */
		size++;
	}

	/**
	 * Looks up the node associated to the key k. Needs logarithmic time in
	 * average.
	 * 
	 * @param k
	 *            The key for which the node should be found.
	 * @return null, if k is not contained in this skiplist, or the internal
	 *         node with key k, otherwise.
	 */
	Node findnode(K k) {
		/*
		 * start looking at the head, and start on the highest level, going down
		 * to level 0.
		 */
		Node n = head;
		for (int i = head.succ.size() - 1; i >= 0; i--)
			/*
			 * step forwards on i-th level until tail is reached or until the
			 * successor has a higher key than k. Then go down own level and
			 * continue search.
			 */
			while (n.succ.get(i) != tail && n.succ.get(i).key.compareTo(k) <= 0)
				n = n.succ.get(i);
		/*
		 * finally look whether we have found the key, i.e., whether in n the
		 * key k is stored.
		 */
		if (n == head || !n.key.equals(k))
			return null;
		return n;
	}

	/**
	 * Looks up the data associated to the key k. Needs logarithmic time in
	 * average.
	 * 
	 * @param k
	 *            The key for which the data should be found.
	 * @return null, if there is no data for k, or the associated data,
	 *         otherwise.
	 * @see Map#find(Object)
	 */
	public D find(K k) {
		/*
		 * just use the findnode method
		 */
		Node n = findnode(k);
		if (n == null)
			return null;
		return n.data;
	}

	/**
	 * Checks whether the key is present in this skiplist (in O(log n) time in
	 * average).
	 * 
	 * @param k
	 *            The key to look for.
	 * @return true, iff the k is present in this skiplist.
	 * @see Map#contains(Object)
	 */
	public boolean contains(K k) {
		/*
		 * just use the findnode method
		 */
		return findnode(k) != null;
	}

	/**
	 * Deletes the key-data association for the given key in this skiplist in
	 * O(log n) in average.
	 * 
	 * @param k
	 *            The key which should be deleted
	 * @see Map#delete(Object)
	 */
	public void delete(K k) {
		Node n = head;
		/*
		 * if the key is not present in this list, then do nothing, otherwise
		 * decrease size of this list and continue
		 */
		if (contains(k))
			size--;
		else
			return;
		/*
		 * start traversing from head and from the highest level. Then
		 * repeatedly delete on i-th level.
		 */
		for (int i = head.succ.size() - 1; i >= 0; i--) {
			/*
			 * traverse until the successor of n is the tail or it has a
			 * (non-strictly) higher key than k.
			 */
			while (n.succ.get(i) != tail && n.succ.get(i).key.compareTo(k) < 0)
				n = n.succ.get(i);
			/*
			 * if the successor has the key we want to delete, then delete it
			 * from the i-th level
			 */
			if (n.succ.get(i) != tail && n.succ.get(i).key.equals(k))
				n.succ.set(i, n.succ.get(i).succ.get(i));
		}
	}

	/**
	 * Returns the size of this skiplist (in constant time).
	 * 
	 * @return The size of this skiplist.
	 * @see Map#size()
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns whether this skiplist is empty (in constant time).
	 * 
	 * @return true, if this list is empty, and false, otherwise.
	 * @see Map#isempty()
	 */
	public boolean isempty() {
		return this.size == 0;
	}

	public Iterator<K, D> iterator() {
		return new Skiplistiterator();
	}

	/**
	 * This class is used to iterate over a skiplist. This can be done by
	 * traversing the lowest level, i.e., level 0. Then only the current node
	 * has to be stored in the iterator.
	 */
	class Skiplistiterator implements Iterator<K, D> {
		Node n;

		public Skiplistiterator() {
			n = head.succ.get(0);
		}

		public K key() {
			return n.key;
		}

		public D data() {
			return n.data;
		}

		public boolean more() {
			return n != tail;
		}

		public void step() {
			n = n.succ.get(0);
		}
	}

	public void print() {
		Node n = head.succ.get(0);
		while (n != tail) {
			System.out.println(n.key);
			n = n.succ.get(0);
		}
	}

}