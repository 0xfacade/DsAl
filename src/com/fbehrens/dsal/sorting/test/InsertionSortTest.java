package com.fbehrens.dsal.sorting.test;

import com.fbehrens.dsal.sorting.InsertionSort;
import com.fbehrens.dsal.sorting.SortingAlgorithm;

public class InsertionSortTest extends SortingTest {

    public <T extends Comparable<T>> SortingAlgorithm<T> getAlg() {
        return new InsertionSort<>();
    }
}
