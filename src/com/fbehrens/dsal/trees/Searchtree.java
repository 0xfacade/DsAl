package com.fbehrens.dsal.trees;
import com.fbehrens.dsal.*;
import com.fbehrens.dsal.arrays.*;
/**
 * Traverses all elements of a searchtree in pre-order, in-order, or post-order.
 * (One needs linear time in nr of nodes to traverse all nodes,
 *  and one needs at only constant space.) 
 * 
 * @author thiemann
 */
class SearchtreeIterator<K extends Comparable<K>,D> implements Iterator<K,D> {

    private static enum Direction {LEFT, ELEMENT, RIGHT, UP};
    
    /*
     * the current node we are looking at, null if we are done.
     */
    private Searchtreenode<K,D> node;
    /*
     * the previous node we traversed before node 
     * (this is not the node returned before the current node
     *  as result of the iterator)
     */
    private Searchtreenode<K,D> prev;
    /*
     * The direction we came from when going from prev to node.
     */
    private Direction dir;
    /*
     * gives the traversing order, when to output the elements.
     * (when coming from Up(pre), Left(in), or Right(post-order))
     */    
    private final Direction order;
    
    /**
     * Constructs a new iterator which traverses the tree with the given root node.
     * @param root The root node of the tree to traverse.
     * @param order The order in which to traverse this tree (1 = preorder, 2 = inorder, 3 = postorder).
     */
    public SearchtreeIterator(Searchtreenode<K,D> root, int order) {
        this.order = order == 1 ? Direction.UP : (order == 2 ? Direction.LEFT : Direction.RIGHT);
        this.prev = null;
        this.node = root;
        this.dir = Direction.UP;
        this.walkNext(this.order);        
    }
    
    /**
     * Walks to the next node that has to be visited.
     * If stopAt is null, then at least one step will be walked 
     */
    private void walkNext(Direction stopAt) {
        while (this.node != null && this.dir != stopAt) {
            switch (this.dir) {
            case UP: // first time we visit the node
                if (this.node.left == null) {
                    this.dir = Direction.LEFT;
                } else {
                    this.prev = this.node;
                    this.node = this.node.left;
                    // dir remains UP
                }
                break;
            case LEFT: // second time we visit the node
                if (this.node.right == null) {
                    this.dir = Direction.RIGHT;                    
                } else {
                    this.prev = this.node;
                    this.node = this.node.right;
                    this.dir = Direction.UP;
                }
                break;
            case RIGHT:
                // third and last time we visit this node
                this.prev = this.node;
                this.node = this.node.parent;
                if (this.node != null) {
                    if (this.node.left == this.prev) {
                        this.dir = Direction.LEFT;
                    } else if (this.node.right == this.prev) {
                        this.dir = Direction.RIGHT;
                    } else {
                        throw new RuntimeException("Internal error in tree traversal");
                    }
                }
                break;
            }
            stopAt = this.order;
        }                    
    }
    
    public D data() {
        return this.node.data;
    }

    public K key() {
        return this.node.key;
    }

    public boolean more() {
        return this.node != null;
    }

    public void step() {
        this.walkNext(null);
    }
    
}


/**
 * A Searchtree is a {@link Map}-implementation as (not necessarily balanced) 
 * binary tree. Standard access methods like insertion, deletion, and lookup are linear
 * in the depth of the tree, and thus, logarithmic in the nr of entries, 
 * if the tree is balanced.
 * 
 * @author Rossmanith
 *
 * @param <K> The type of the keys.
 * @param <D> The type of the data.
 */
public class Searchtree<K extends Comparable<K>,D>
	                  extends Dictionary<K,D> {
  protected Searchtreenode<K,D> root;

  public Searchtree() { root = null; }

  public void insert(K k, D d) {
    if(root==null) root = new Searchtreenode<K,D>(k, d);
    else root.insert(new Searchtreenode<K,D>(k, d));
  }

  /**
   * Removes the entry which stores the data for the key k.
   * Idle operations if there is no data stored for k.
   * (linear time in depth of tree)
   * @param k The key to delete with its associated data.
   */
  public void delete(K k) {
    if(root==null) return;
    if(root.key.equals(k))
      if(root.left==null && root.right==null) {
        root=null; return;
      }
      else if(root.left==null) {
        root=root.right; root.parent=null; return;
      }
    /*
     * note that if a left child is present, then the n.delete()
     * call will not try to eliminate the node n itself. Hence,
     * in that case we can also make a call to root.delete() 
     * without updating the root as attribute of this Searchtree.
     */
    Searchtreenode<K,D> n = root.findsubtree(k);
    if(n!=null) n.delete();
  }

  /**
   * Looks up the data for the given key k.
   * (linear time in depth of the tree)
   * @param k The key to look for, must be non-null.
   * @return The data stored under k, or null, if there is no data for k.
   * @see Map#find(Object)
   */
  public D find(K k) {
    if(root==null) return null;
    Searchtreenode<K,D> n = root.findsubtree(k);
    return n==null ? null : n.data;
  }

  /**
   * Checks whether the given key k is present in this
   * searchtree.
   * (linear time in depth of the tree)
   * @param k The key to look for, must be non-null.
   * @return true, iff k is contained in this tree.
   */
  public boolean iselement(K k) {
    return root!=null && root.findsubtree(k)!=null;
  }

  public int size() { return root==null ? 0 : root.size(); }

  /**
   * Repairs the root node if a sub-node is currently stored
   * as root. (Which may happen after calling rotate-methods for example)
   * @see Searchtreenode#rotateleft()    
   * @see Searchtreenode#rotateright()
   * @see Treap#rotate_up(Treapnode)
   * @see Treap#rotate_down(Treapnode)    
   */
  void repair_root() {
    if(root==null) return;
    while(root.parent!=null) root=root.parent;
  }

  public Iterator<K,D> iterator() {      
    return new SearchtreeIterator<K,D>(this.root,2);    
  }

  public void print() {
    if(root!=null) root.printindent(0);
  }

  

  /**
   * Constructs an optimal search tree in the general case 
   * with n given keys where
   * the propabilities to access each key are stored in p
   * and the propabilities to access values between two keys
   * are stored in q. The sum of all values in p and q must be 1.
   * (Implemented with dynamic programming, O(n<sup>3</sup>) complexity)
   * @param n The number of keys.
   * @param keys The keys to store.
   * @param p The propabilities to access a key. More precisely,
   *           p[i] is the propability to access keys[i] for all
   *           0 &le; i < n.
   * @param q The propabilities to access values between two keys.
   *   More precisely, q[0] is the propability to ask for values smaller
   *   than keys[0]. q[n] is the propability to ask for values larger than
   *   keys[n-1]. And q[i] is the propability to ask for values between
   *   keys[i-1] and keys[i] for all 1 &le; i < n.
   */
  public void opt_searchtree(int n, Array<K> keys,
		Array<Double> p, Array<Double> q) {
    double[][] e = new double[n+2][n+1];
    double[][] w = new double[n+2][n+1];
    int[][] root = new int[n+2][n+1];
    for(int i=1; i<=n+1; i++) w[i][i-1]=e[i][i-1]=q.get(i-1);
    for(int l=0; l<=n; l++)
      for(int i=1; i+l<=n; i++) {
	e[i][i+l] = Double.MAX_VALUE;
	w[i][i+l] = w[i][i+l-1] + p.get(i+l) + q.get(i+l);
	for(int r=i; r<=i+l; r++) {
	  Double t = e[i][r-1]+e[r+1][i+l]+w[i][i+l];
	  if(t<e[i][i+l]) { e[i][i+l] = t; root[i][i+l] = r; }
	}
      }
    construct_opt_tree(1, n, keys, root);
  }

  /*
    System.err.println("W[][]");
    for(int i=1; i<=n; i++) {
      for(int j=1; j<=n; j++) System.err.print(" "+w[i][j]);
      System.err.println();
    }
    System.err.println("E[][]");
    for(int i=1; i<=n; i++) {
      for(int j=1; j<=n; j++) System.err.print(" "+e[i][j]);
      System.err.println();
    }
  */

  /**
   * Helper method to construct optimal search trees.
   * Inserts all keys from index i to j, and decides
   * on the values root computed by the {@link #opt_searchtree(int, Array, Array, Array)}
   * which value to insert first.
   * @param i The left bound on the keys to store.
   * @param j The right bound on the keys to store
   * @param keys The keys to store from index i to index j
   * @param root The decision table which contains information
   *  for every combination (i,j) which element (between i and j) 
   *  should be put first into the searchtree.
   */
  void construct_opt_tree(int i, int j,
		    Array<K> keys, int[][] root) {
    if(j<i) return;
    int r = root[i][j];
    insert(keys.get(r), null);
    construct_opt_tree(i, r-1, keys, root); 
    construct_opt_tree(r+1, j, keys, root); 
  }

  
}

/**
 * A class which represents the nodes of a {@link Searchtree}.
 * @author Rossmanith
 *
 * @param <K> The type of the keys.
 * @param <D> The type of the data.
 */
class Searchtreenode<K extends Comparable<K>,D> {
  /*
   * Key and data stored in this node. Key must
   * be non-null.
   */
  K key;
  D data;
  /*
   * links to the left child, right child, and to the parent 
   * (null, if the corresponding links do not exist) 
   */
  Searchtreenode<K,D> left, right, parent;

  /**
   * Constructs a new node with key k and data d, but
   * without any links.
   * @param k The key, must be non-null.
   * @param d The data to store under k.
   */
  public Searchtreenode(K k, D d) {
    left = right = parent = null;
    key = k; data = d;  
  }

  /**
   * Copies key and data of the node n into this node.
   * @param n The node from which key and data are taken.
   */
  public void copy(Searchtreenode<K,D> n) { key = n.key; data = n.data;    
  }


  /**
   * Deletes this node from the searchtree. 
   * (Linear in depth of tree)
   */
  void delete() {
    if(left==null && right==null) {
      if(parent.left==this) parent.left=null;
      else parent.right=null; }
    else if(left==null) {
      if(parent.left==this)  parent.left = right;
      else parent.right = right;
      right.parent = parent; }
    else {
      Searchtreenode<K,D> max = left;
      while(max.right!=null) max=max.right;
      copy(max); max.delete();
    }
  }

  /**
   * Inserts the node n. If the key k of n is not present below
   * this node, then n will become a leaf, otherwise the
   * previously stored data for k will be overwritten by the
   * new data of n.
   * (Linear in depth of tree)
   * @param n A node containing the key and value to store. The
   *        key must be non-null. Moreover, n must not have left- or
   *        right-links. 
   */
  public void insert(Searchtreenode<K,D> n) {
    int c = n.key.compareTo(key);
    if(c<0) {
        if(left!=null) left.insert(n);
	else { left = n; left.parent = this; }
    }
    else if(c>0) {
        if(right!=null) right.insert(n);
        else { right = n; right.parent = this; }
    }
    else copy(n);
  }

  /**
   * Computes the number of nodes of the tree 
   * represented by this.
   * (linear time operation)
   */
  public int size() {
    int s=1;
    if(left!=null) s += left.size();
    if(right!=null) s += right.size();
    return s;
  }

  /**
   * Finds the node which stores the key k.
   * (Linear time operation in depth of tree) 
   * @param k The key to look for.
   * @return The node with key k, if it is present below this node, or
   *          null, otherwise.
   */
  Searchtreenode<K,D> findsubtree(K k) {
    int c = k.compareTo(key);
    if(c>0) return right==null ? null : right.findsubtree(k);
    else if(c<0) return left==null ? null : left.findsubtree(k);
    else return this;
  }

  /**
   * Prints the tree below this node with the given indention.
   * @param indent The nr of spaces to indent.
   */
  void printindent(int indent) {
    if(left!=null) left.printindent(indent+2);
    for(int i=0; i<indent; i++) System.out.print(" ");
    System.out.println(key+"("+data+")");
    if(right!=null) right.printindent(indent+2);
  }

 /**
  * When applied to this (=t) the following happens:
  * <pre>
  *       p                p
  *      /                /
  *     t                a
  *    / \    ====>     / \
  *   a   d            b   t
  *  / \                  / \
  * b   c                c   d
  * </pre>
  * t is rotated to the right.  
  * An ordered search tree remains ordered.
  * Note that any b, c, d, or p can be missing.  Only t and a have
  * to be present, i.e., t itself and its left child.
  */
  /*
   * Note that this method may yield a new node which is 
   * now root of the tree. Therefore, one must call repair_root
   * after performing an rotation.
   */
  void rotateright() {
    Searchtreenode<K,D> p, a, b, c, d;
    p = this.parent;
    a = this.left; d = this.right;
    b = a.left; c = a.right;
    if(p!=null) {
      if(p.left==this) p.left = a;
      else p.right = a;
    }
    a.right = this; a.parent = p;
    this.left = c; this.parent = a;
    if(c!=null) c.parent = this;
  }

  /**
   * When applied to this (=t) the following happens:
   * <pre>
   * p                p    
   *  \                \  
   *   t                a
   *  / \    ====>     / \
   * d   a            t   b
   *    / \          / \    
   *   c   b        d   c    
   * </pre>
   * t is rotated to the left.  
   * An ordered search tree remains ordered.
   * Note that any b, c, d, or p can be missing.  Only t and a have
   * to be present, i.e., t itself and its right child.
   */
  /*
   * Note that this method may yield a new node which is 
   * now root of the tree. Therefore, one should call repair_root
   * after performing an rotation.
   */
  void rotateleft() {
    Searchtreenode<K,D> p, a, b, c, d;
    p = this.parent;
    a = this.right; d = this.left;
    b = a.right; c = a.left;
    if(p!=null) {
      if(p.right==this) p.right = a;
      else p.left = a;
    }
    a.left = this; a.parent = p;
    this.right = c; this.parent = a;
    if(c!=null) c.parent = this;
    
  }

  /**
   * Computes the height of the subtree below this node.
   */
  public int height()
  {
    if(key==null) return 0;
    int hl=0, hr=0;
    if(left!=null) hl = left.height();
    if(right!=null) hr = right.height();
    return hl>hr ? hl+1 : hr+1;
  }

}
