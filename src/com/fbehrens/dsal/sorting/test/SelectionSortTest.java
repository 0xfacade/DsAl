package com.fbehrens.dsal.sorting.test;

import com.fbehrens.dsal.sorting.SelectionSort;
import com.fbehrens.dsal.sorting.SortingAlgorithm;

public class SelectionSortTest extends SortingTest {

    public <T extends Comparable<T>> SortingAlgorithm<T> getAlg() {
        return new SelectionSort<>();
    }
}
