package com.fbehrens.dsal.sorting;

import java.util.Arrays;

public class SelectionSort<T extends Comparable<T>> extends SortingAlgorithm<T> {
    @Override
    public T[] sorted(T[] input) {
        T[] other = Arrays.copyOf(input, input.length);
        inPlace(other);
        return other;
    }

    @Override
    public void inPlace(T[] input) {
        for (int i = 0; i < input.length; i++) {
            int minIndex = i;
            T minValue = input[i];
            for (int j = i + 1; j < input.length; j++) {
                if (minValue.compareTo(input[j]) > 0) {
                    minIndex = j;
                    minValue = input[j];
                }
            }
            swap(input, i, minIndex);
        }
    }
}
