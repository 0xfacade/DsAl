package com.fbehrens.dsal.sorting;

public abstract class SortingAlgorithm<T extends Comparable<T>> {
    public abstract T[] sorted(T[] input);
    public abstract void inPlace(T[] input);

    <U> void swap(U[] array, int i, int j) {
        U temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
