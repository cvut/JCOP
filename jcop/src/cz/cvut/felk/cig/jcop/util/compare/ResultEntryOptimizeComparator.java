/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.util.compare;

import cz.cvut.felk.cig.jcop.result.ResultEntry;

import java.util.Comparator;

/**
 * Compares two result entries by their optimize counter.
 *
 * @author Ondrej Skalicka
 */
public class ResultEntryOptimizeComparator implements Comparator<ResultEntry> {
    /**
     * If true, elements are sorted in ascending order.
     */
    protected boolean ascending;

    /**
     * Allows change if elements are sorted in ascending or descending order.
     *
     * @param ascending if true, elements are sorted in ascending order
     */
    public ResultEntryOptimizeComparator(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * Sorts elements in ascending order.
     */
    public ResultEntryOptimizeComparator() {
        this(true);
    }

    public int compare(ResultEntry o1, ResultEntry o2) {
        int thatVal = o1.getOptimizeCounter();
        int anotherVal = o2.getOptimizeCounter();

        if (this.ascending) return (thatVal < anotherVal ? -1 : (thatVal == anotherVal ? 0 : 1));
        else return (thatVal < anotherVal ? 1 : (thatVal == anotherVal ? 0 : -1));
    }
}
