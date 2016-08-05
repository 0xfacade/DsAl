package com.fbehrens.dsal;
/**
 * 
 * A pair consists of two values of type A and B.
 *
 * @author Rossmanith
 * @param <A> The type of the first value.
 * @param <B> The type of the second value.
 */
public class Pair<A,B> {
    /**
     * First value
     */
    protected A a;
    /**
     * Second value
     */
    protected B b;
    /**
     * Create a new pair with the two values a and b. 
     * @param a the first value
     * @param b the second value
     */
    public Pair(A a, B b) { this.a = a; this.b = b; }
    /**
     * Setter for first value
     * @param a the new first value
     */
    public void setFirst(A a) { this.a = a; }
    /**
     * Setter for second value
     * @param b the new second value
     */
    public void setSecond(B b) { this.b = b; }
    /**
     * Selector of the first value
     * @return the first value of this pair
     */
    public A first() { return a; }
    /**
     * Selector of the second value
     * @return the second value of this pair
     */
    public B second() { return b; }
    public String toString() { return "["+a+","+b+"]"; }
}
