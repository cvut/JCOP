/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.knapsack;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.util.NoSuchElementException;

/**
 * Iterator over knapsack operations.
 * <p/>
 * For every knapsack item, iterator returns either {@link AddOperation} or {@link RemoveOperation}, depending if item
 * is in configuration present or not. Items with lower index are parsed prior to items with higher index.
 *
 * @author Ondrej Skalicka
 */
public class KnapsackIterator implements OperationIterator {
    /**
     * Counter indicating which item to change (add or remove to/from knapsack).
     */
    protected int counter = 0;
    /**
     * Configuration to generate operations for.
     */
    protected Configuration configuration;
    /**
     * Knapsack problem which contains operations
     */
    protected Knapsack problem;

    /**
     * Creates iterator for given configuration and problem.
     *
     * @param configuration configuration to generate operations for
     * @param problem       knapsack problem which contains operations
     */
    public KnapsackIterator(Configuration configuration, Knapsack problem) {
        this.configuration = configuration;
        this.problem = problem;
        this.counter = 0;
    }

    public boolean hasNext() {
        return this.counter < this.problem.knapsackItems.size();
    }

    public Operation next() throws NoSuchElementException {
        if (this.counter >= this.problem.knapsackItems.size())
            throw new NoSuchElementException("Knapsack iterator has no more operations");

        if (this.configuration.valueAt(this.counter) == 1)
            return this.problem.removeOperations.get(this.counter++);
        return this.problem.addOperations.get(this.counter++);
    }

    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("KnapsackIterator does not support remove()");
    }

    public Operation getRandomOperation() throws UnsupportedOperationException {
        int i = JcopRandom.nextInt(this.problem.knapsackItems.size());
        if (this.configuration.valueAt(i) == 1)
            return this.problem.removeOperations.get(i);
        return this.problem.addOperations.get(i);
    }
}
