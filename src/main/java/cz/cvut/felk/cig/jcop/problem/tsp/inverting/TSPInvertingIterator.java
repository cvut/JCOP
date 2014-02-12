/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.tsp.inverting;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.util.NoSuchElementException;

/**
 * Works exactly the same as {@link cz.cvut.felk.cig.jcop.problem.tsp.TSPIterator} with the exception that it returns {@link cz.cvut.felk.cig.jcop.problem.tsp.inverting.InvertPathOperation}.
 *
 * @author Ondrej Skalicka
 */
public class TSPInvertingIterator implements OperationIterator {
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
    protected TSPInverting problem;

    /**
     * Creates new TSP iterator for given problem and configuration.
     *
     * @param configuration configuration to create operations for
     * @param problem       problem of configuration
     */
    public TSPInvertingIterator(Configuration configuration, TSPInverting problem) {
        this.configuration = configuration;
        this.problem = problem;
    }

    /**
     * Returns operation for given counter.
     *
     * @param counter counter to return operation for
     * @return operation for this counter
     */
    protected InvertPathOperation counterToOperation(int counter) {
        return this.problem.invertPathOperations.get(counter);
    }

    public InvertPathOperation getRandomOperation() throws UnsupportedOperationException {
        return this.counterToOperation(JcopRandom.nextInt(this.problem.invertPathOperations.size()));
    }

    public boolean hasNext() {
        return this.counter < this.problem.invertPathOperations.size();
    }

    public InvertPathOperation next() {
        if (!this.hasNext())
            throw new NoSuchElementException("TSP iterator has no more operations");
        return this.counterToOperation(this.counter++);
    }

    public void remove() {
        throw new UnsupportedOperationException("TSP iterator does not support remove()");
    }
}
