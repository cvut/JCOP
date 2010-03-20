/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.util.NoSuchElementException;

/**
 * Iterates over {@link cz.cvut.felk.cig.jcop.problem.tsp.SwitchCityOperation switch city operations} of TSP problem.
 * <p/>
 * Operations are ordered such that lower source index always precedes higher source index, lower source index precedes
 * higher destination index for same source index and source index is always lower than destination index.
 * <p/>
 * Example:
 * <p/>
 * For dimension 4 problem, pairs (source, destination) would be
 * <p/>
 * (0,1), (0,2), (0,3), (1,2), (1,3), (2,3)
 * <p/>
 * There are always dimension*(dimension-1)/2 operations for every configuration.
 *
 * @author Ondrej Skalicka
 */
public class TSPIterator implements OperationIterator {
    /**
     * Active index to return operation for.
     */
    protected int counter = 0;
    /**
     * Configuration to iterate over
     */
    protected Configuration configuration;
    /**
     * TSP problem
     */
    protected TSP problem;

    /**
     * Creates new TSP iterator for given problem and configuration.
     *
     * @param configuration configuration to create operations for
     * @param problem       problem of configuration
     */
    public TSPIterator(Configuration configuration, TSP problem) {
        this.configuration = configuration;
        this.problem = problem;
    }

    /**
     * Returns operation for given counter.
     *
     * @param counter counter to return operation for
     * @return operation for this counter
     */
    protected SwitchCityOperation counterToOperation(int counter) {
        return this.problem.switchCityOperations.get(counter);
    }

    public SwitchCityOperation getRandomOperation() throws UnsupportedOperationException {
        return this.counterToOperation(JcopRandom.nextInt(this.problem.getDimension() * (this.problem.getDimension() - 1) / 2));
    }

    public boolean hasNext() {
        return this.counter < this.problem.getDimension() * (this.problem.getDimension() - 1) / 2;
    }

    public SwitchCityOperation next() {
        if (!this.hasNext())
            throw new NoSuchElementException("TSP iterator has no more operations");
        return this.counterToOperation(this.counter++);
    }

    public void remove() {
        throw new UnsupportedOperationException("TSP iterator does not support remove()");
    }
}
