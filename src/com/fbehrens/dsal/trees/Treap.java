package com.fbehrens.dsal.trees;

import java.util.Random;

import com.fbehrens.dsal.Map;

/**
 * A treapnode is like a {@link Searchtreenode} but additionally a weight is
 * stored.
 * 
 * @author Rossmanith
 *
 * @param <K>
 *            The type of keys to store.
 * @param <D>
 *            The type of data to store.
 */
class Treapnode<K extends Comparable<K>, D> extends Searchtreenode<K, D> {
	int weight;

	/**
	 * Copies key and data of the node n into this node. This includes the
	 * weight.
	 * 
	 * @param n
	 *            The node from which key and data are taken.
	 * @see Searchtreenode#copy(Searchtreenode)
	 */
	public void copy(Treapnode<K, D> n) {
		super.copy(n);
		weight = n.weight;
	}

	/**
	 * Constructs a new treap node with random weight.
	 * 
	 * @param k
	 *            The key for the new node.
	 * @param d
	 *            The data for the new node.
	 * @param generator
	 *            The random number generator to generate the weight.
	 * @see Searchtreenode#Searchtreenode(Comparable, Object)
	 */
	public Treapnode(K k, D d, Random generator) {
		super(k, d);
		weight = generator.nextInt(10000);
	}

	/*
	 * in comparison to super-implementation here we output also the weights.
	 */
	void printindent(int indent) {
		if (key == null)
			return;
		if (left != null)
			left.printindent(indent + 2);
		for (int i = 0; i < indent; i++)
			System.out.print(" ");
		System.out.println(key + "(" + data + ")[" + weight + "]");
		if (right != null)
			right.printindent(indent + 2);
	}

}

/**
 * A treap is like a search-tree where the nodes are annotated with
 * weights/priorities. Then it is assured that the nodes are a heap w.r.t. to
 * the weights and a search-tree w.r.t. the keys, i.e., for every node its
 * weight is smaller than that of all nodes below it. Insertion, deletion, etc.
 * are performed in logarithmic time in average.
 * 
 * @author Rossmanith
 *
 * @param <K>
 *            The type of keys to store.
 * @param <D>
 *            The type of data to store.
 */
public class Treap<K extends Comparable<K>, D> extends Searchtree<K, D> {
	Random generator;

	/**
	 * Constructs a new, empty Treap
	 */
	public Treap() {
		generator = new Random();
	}

	/**
	 * Constructs a new, empty Treap with given random seed.
	 * 
	 * @param seed
	 *            The random seed to initialize the random number generator.
	 */
	public Treap(int seed) {
		generator = new Random(seed);
	}

	/**
	 * Associates the data d to the key k in this map. (Internally, a random
	 * weight is taken for the new node)
	 * 
	 * @param k
	 *            The key to store.
	 * @param d
	 *            The data to store.
	 * @see Map#insert(Object, Object)
	 */
	public void insert(K k, D d) {
		if (root == null)
			root = new Treapnode<K, D>(k, d, generator);
		else {
			/*
			 * first insert node, then rotate it upwards.
			 */
			root.insert(new Treapnode<K, D>(k, d, generator));
			rotate_up((Treapnode<K, D>) root.findsubtree(k));
		}
	}

	/**
	 * Associates the data d to the key k in this map. Moreover, not a random
	 * weight is taken but the user provided weight w.
	 * 
	 * @param k
	 *            The key to store.
	 * @param d
	 *            The data to store.
	 * @param weight
	 *            The weight for the newly inserted node.
	 * @see Map#insert(Object, Object)
	 */
	public void insert(K k, D d, int weight) {
		if (root == null) {
			root = new Treapnode<K, D>(k, d, generator);
			((Treapnode<K, D>) root).weight = weight;
		} else {
			Treapnode<K, D> n = new Treapnode<K, D>(k, d, generator);
			n.weight = weight;
			/*
			 * first insert node, then rotate it upwards.
			 */
			root.insert(n);
			rotate_up((Treapnode<K, D>) root.findsubtree(k));
		}
	}

	/**
	 * Removes the entry which stores the data for the key k. Idle operations if
	 * there is no data stored for k. (logarithmic time in average)
	 * 
	 * @param k
	 *            The key to delete with its associated data.
	 */
	public void delete(K k) {
		if (root == null)
			return;
		Treapnode<K, D> n = (Treapnode<K, D>) root.findsubtree(k);
		if (n == null)
			return;
		rotate_down(n);
		super.delete(k);
	}

	/**
	 * Returns the priority(weight) for the node stored under key k.
	 * 
	 * @param k
	 *            A key which must be present in this treap.
	 * @return The priority of the node containing k.
	 */
	public int getpriority(K k) {
		return ((Treapnode<K, D>) root.findsubtree(k)).weight;
	}

	/**
	 * Rotates a node upwards, until the treap does not violate the heap
	 * property for node n any more.
	 * 
	 * @param n
	 *            The node which may currently violate the heap property.
	 */
	void rotate_up(Treapnode<K, D> n) {
		while (true) {
			if (n.parent == null)
				break;
			if (((Treapnode<K, D>) n.parent).weight <= n.weight)
				break;
			if (n.parent.right == n)
				n.parent.rotateleft();
			else
				n.parent.rotateright();
		}
		/*
		 * After rotating the root node of this treap may have changed. The
		 * corresponding update of this.root is done by repair_root.
		 */
		repair_root();
	}

	/**
	 * Rotates a node downwards until it becomes a leaf. Note that this method
	 * destroys the heap property unless n is deleted afterwards.
	 * 
	 * @param n
	 *            The node which will be a leaf afterwards and should be deleted
	 *            to reestablish the heap-property.
	 */
	void rotate_down(Treapnode<K, D> n) {
		while (true) {
			if (n.left != null) {
				if (n.right == null || ((Treapnode<K, D>) n.left).weight <= ((Treapnode<K, D>) n.right).weight)
					n.rotateright();
				else
					n.rotateleft();
			} else if (n.right == null)
				break;
			else
				n.rotateleft();
		}
		repair_root();
	}

}
