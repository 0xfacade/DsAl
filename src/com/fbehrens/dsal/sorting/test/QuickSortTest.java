package com.fbehrens.dsal.sorting.test;

import com.fbehrens.dsal.sorting.QuickSort;
import com.fbehrens.dsal.sorting.SortingAlgorithm;

public class QuickSortTest extends SortingTest {

    public <T extends Comparable<T>> SortingAlgorithm<T> getAlg() {
        return new QuickSort<>();
    }
}
