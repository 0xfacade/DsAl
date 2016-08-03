package com.fbehrens.dsal.sorting;

import java.util.Arrays;

public class QuickSort<T extends Comparable<T>> extends SortingAlgorithm<T> {

    @Override
    public T[] sorted(T[] input) {
        T[] other = Arrays.copyOf(input, input.length);
        inPlace(other);
        return other;
    }

    @Override
    public void inPlace(T[] input) {
        if (input.length == 0) {
            return;
        }

        int maxIndex = 0;
        T maximum = input[0];
        for (int i = 1; i < input.length; i++) {
            if (input[i].compareTo(maximum) > 0) {
                maxIndex = i;
                maximum = input[i];
            }
        }
        swap(input, maxIndex, input.length - 1);
        sort(input, 0, input.length - 1);
    }

    private void sort(T[] input, int lower, int upper) {
        if (lower >= upper) {
            return;
        }

        int l = lower + 1;
        int r = upper;
        while (l <= r) {
            while (input[l].compareTo(input[lower]) < 0) {
                l++;
            }
            while (input[r].compareTo(input[lower]) > 0) {
                r--;
            }
            swap(input, l, r);
            r--;
        }
        r++;
        swap(input, l, r);
        swap(input, r, lower);
        sort(input, lower, r - 1);
        sort(input, r + 1, upper);
    }
}
