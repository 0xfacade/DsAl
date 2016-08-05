package com.fbehrens.dsal.sorting;

import java.util.Arrays;

public class InsertionSort<T extends Comparable<T>> extends SortingAlgorithm<T> {
    @Override
    public T[] sorted(T[] input) {
        T[] other = Arrays.copyOf(input, input.length);
        inPlace(other);
        return other;
    }

    @Override
    public void inPlace(T[] input) {
        for (int i = 0; i < input.length; i++) {
            int j = i;
            while (j >= 1 && input[j].compareTo(input[j - 1]) < 0) {
                swap(input, j - 1, j);
                j--;
            }
        }
    }
}
