package com.fbehrens.dsal.sorting.competition;

import java.util.Random;

import com.fbehrens.dsal.RuntimeComparator;
import com.fbehrens.dsal.sorting.InsertionSort;
import com.fbehrens.dsal.sorting.QuickSort;
import com.fbehrens.dsal.sorting.SelectionSort;
import com.fbehrens.dsal.sorting.SortingAlgorithm;

public class RandomSort implements RuntimeComparator.CompetitorGroup {
    private static long seed = System.currentTimeMillis();

    private static void sortRandomArrays(SortingAlgorithm<Integer> sorter) {
        Random r = new Random(seed);
        Integer[] test = new Integer[10000];
        for (int iteration = 0; iteration < 10; iteration++) {
            for (int i = 0; i < 10000; i++) {
                test[i] = r.nextInt(200001) - 100000; // -100000 ... 100000
            }
            sorter.inPlace(test);
        }
    }

    private static class SelectionSortCompetitor implements RuntimeComparator.Competitor {
        @Override
        public void run() {
            SortingAlgorithm<Integer> alg = new SelectionSort<>();
            sortRandomArrays(alg);
        }
    }

    private static class InsertionSortCompetitor implements RuntimeComparator.Competitor {
        @Override
        public void run() {
            SortingAlgorithm<Integer> alg = new InsertionSort<>();
            sortRandomArrays(alg);
        }
    }

    private static class QuickSortCompetitor implements RuntimeComparator.Competitor {
        @Override
        public void run() {
            SortingAlgorithm<Integer> alg = new QuickSort<>();
            sortRandomArrays(alg);
        }
    }

    public RuntimeComparator.Competitor[] getCompetitors() {
        return new RuntimeComparator.Competitor[] { new SelectionSortCompetitor(), new InsertionSortCompetitor(), new QuickSortCompetitor() };
    }
}
