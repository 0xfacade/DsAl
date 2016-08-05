package com.fbehrens.dsal.sorting.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.fbehrens.dsal.sorting.SortingAlgorithm;

public abstract class SortingTest {
    private static Random random = new Random();
    private SortingAlgorithm<Integer> intSorter;
    private SortingAlgorithm<String> stringSorter;

    public abstract <T extends Comparable<T>> SortingAlgorithm<T> getAlg();

    @Before
    public void setUp() {
        intSorter = getAlg();
        stringSorter = getAlg();
    }

    @Test
    public void testEmpty() {
        Integer[] empty = new Integer[] { };
        Integer[] sorted = intSorter.sorted(empty);
        assertArrayEquals(new Integer[] { }, empty);
        assertArrayEquals(new Integer[] { }, sorted);
        assertNotEquals(empty, sorted);
        intSorter.inPlace(empty);
        assertArrayEquals(new Integer[] { }, empty);
    }

    @Test
    public void testOneElement() {
        String[] one = new String[] { "hello" };
        String[] sorted = stringSorter.sorted(one);
        assertArrayEquals(new String[] { "hello" }, one);
        assertArrayEquals(new String[] { "hello" }, sorted);
        assertNotEquals(one, sorted);
        stringSorter.inPlace(one);
        assertArrayEquals(new String[] { "hello" }, one);
    }

    @Test
    public void testTwoElements() {
        Integer[] ascending = new Integer[] { 3, 5 };
        Integer[] descending = new Integer[] { 5, 3 };
        Integer[] sortedAsc = intSorter.sorted(ascending);
        Integer[] sortedDesc = intSorter.sorted(descending);
        assertArrayEquals(new Integer[] { 3, 5 }, ascending);
        assertArrayEquals(new Integer[] { 5, 3 }, descending);
        assertArrayEquals(new Integer[] { 3, 5 }, sortedAsc);
        assertArrayEquals(new Integer[] { 3, 5 }, sortedDesc);
        assertNotEquals(ascending, sortedAsc);
        assertNotEquals(descending, sortedDesc);
        intSorter.inPlace(ascending);
        intSorter.inPlace(descending);
        assertArrayEquals(new Integer[] { 3, 5 }, ascending);
        assertArrayEquals(new Integer[] { 3, 5 }, descending);
    }

    @Test
    public void testSorted() {
        Integer[] testCase = new Integer[] { -5, 0, 3, 9, 9, 11, 15, 17, 18 };
        Integer[] sorted = intSorter.sorted(testCase);
        assertArrayEquals(testCase, sorted);
        assertNotEquals(testCase, sorted);
    }

    @Test
    public void testReverse() {
        Integer[] testCase = new Integer[] { 17, 5, -3, -9, -55 };
        Integer[] sorted = intSorter.sorted(testCase);
        assertArrayEquals(new Integer[] { -55, -9, -3, 5, 17 }, sorted);
    }

    @Test
    public void testRandom() {
        Integer[] testCase = new Integer[100];
        for (int i = 0; i < 100; i++) {
            testCase[i] = random.nextInt(10000);
        }
        Integer[] sorted = intSorter.sorted(testCase);
        for (int i = 0; i < 99; i++) {
            assertTrue(sorted[i] <= sorted[i + 1]);
        }
    }
}
