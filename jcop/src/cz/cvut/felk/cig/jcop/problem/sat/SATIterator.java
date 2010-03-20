/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.util.NoSuchElementException;

/**
 * SAT operation iterator.
 * <p/>
 * Iterates over all variables, for each returning either {@link SetFalseOperation} (if variable in configuration is 1)
 * or {@link SetTrueOperation} (if variable in configuration is 0).
 *
 * @author Ondrej Skalicka
 */
public class SATIterator implements OperationIterator {
    /**
     * Active index to return operation for.
     */
    protected int counter = 0;
    /**
     * Configuration to iterate over
     */
    protected Configuration configuration;
    /**
     * SAT problem
     */
    protected SAT problem;

    /**
     * Creates new SAT problem iterator with given configuration and problem.
     *
     * @param configuration configuration to iterate over
     * @param problem       problem specifications
     */
    public SATIterator(Configuration configuration, SAT problem) {
        this.configuration = configuration;
        this.problem = problem;
        this.counter = 0;
    }

    public boolean hasNext() {
        return this.counter < this.problem.getDimension();
    }

    public Operation next() throws NoSuchElementException {
        if (this.counter >= this.problem.getDimension())
            throw new NoSuchElementException("SAT iterator has no more operations");

        if (this.configuration.valueAt(this.counter) == 1)
            return this.problem.setFalseOperations.get(this.counter++);
        return this.problem.setTrueOperations.get(this.counter++);
    }

    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("SAT does not support remove()");
    }

    public Operation getRandomOperation() throws UnsupportedOperationException {
        int i = JcopRandom.nextInt(this.problem.getDimension());

        if (this.configuration.valueAt(i) == 1)
            return this.problem.setFalseOperations.get(i);
        return this.problem.setTrueOperations.get(i);
    }
}
