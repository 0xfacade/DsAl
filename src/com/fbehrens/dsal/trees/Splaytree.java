package com.fbehrens.dsal.trees;
import com.fbehrens.dsal.*;
/**
 * An implementation of a Splaytree.
 * 
 * @author Rossmanith
 *
 * @param <K> The type of the keys.
 * @param <D> The type of the data.
 */
public class Splaytree<K extends Comparable<K>,D> extends Searchtree<K,D> {

  public void insert(K k, D d) {
    /*
     * first search for key k and insert node as a new leaf 
     */
    super.insert(k, d);
    /*
     * then splay the newly created node to the root
     */
    contains(k);
  }

  /*
   * Functionality does not change. But for internal use
   * it is ensured that the possible node containing key k
   * is at the root after this call.
   * @see Searchtree#iselement(K k)
   */
  public boolean contains(K k) {
    if(root==null) return false;
    Searchtreenode<K,D> n=root, last=root;
    int c;
    /*
     * perform search in tree
     */ 
    while(n!=null) {
      last = n;
      c = k.compareTo(n.key);
      if(c<0) n = n.left;
      else if(c>0) n = n.right;
      /*
       * we found the key in n, so splay n such 
       * that n becomes the root
       */
      else { splay(n); return true; }
    }
    /*
     * even if we did not find the key, we perform splay.
     */
    splay(last); return false;
  }

  public D find(K k) {
    /*
     * First perform a search for the key.
     * Afterwards the node with the key will be the root if
     * it exists (see iselement).
     */
    contains(k);
    if(root!=null && root.key.equals(k)) return root.data;
    return null;
  }

  public void delete(K k) {
    if(!contains(k)) return;
    /* 
     * now we know that the node with key k is stored
     * in the root (see iselement).
     */
    if(root.left!=null) {
      Searchtreenode<K,D> max = root.left;
      while(max.right!=null) max=max.right;
      splay(max);
      /*
       * the splay(max) ensures that at this
       * point the node with element k has no left child
       * any more (as the last splay operation is not a zig-zig)
       * and is directly below the root.
       * Hence, the delete operation below is cheap. 
       */
    }
    super.delete(k);
  }

/**
 * Performs the splay-operation on t until
 * t is the root of this tree.
 * @param t The node to move to the root by
 *   applying the splay-operations zig,zag,zig-zig,... repeatedly.
 */
private void splay(Searchtreenode<K,D> t) {
 while(t.parent!=null) {
   if(t.parent.parent==null) {
     if(t==t.parent.left) t.parent.rotateright(); // Zig
     else t.parent.rotateleft(); } // Zag
   else if(t==t.parent.left && t.parent==t.parent.parent.left) {
     t.parent.parent.rotateright(); // Zig-zig
     t.parent.rotateright(); }
   else if(t==t.parent.left && t.parent==t.parent.parent.right) {
       t.parent.rotateright(); // Zig-zag
       t.parent.rotateleft(); }
   else if(t==t.parent.right && t.parent==t.parent.parent.right) {
     t.parent.parent.rotateleft(); // Zag-zag
     t.parent.rotateleft(); }
   else if(t==t.parent.right && t.parent==t.parent.parent.left) {
       t.parent.rotateleft(); // Zag-zig
       t.parent.rotateright(); }
 }
 root = t;
}

}
