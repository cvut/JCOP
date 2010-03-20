/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.jobshop;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.OperationIterator;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.util.NoSuchElementException;

/**
 * Iterates over all possible MoveOperations for a job shop configuration.
 * <p/>
 * Operations are ordered such that for first job all MoveOperations are supplied (to machines with lower number first),
 * then second job ... until last job.
 *
 * @author Ondrej Skalicka
 */
public class JobShopIterator implements OperationIterator {
    /**
     * Active index to return operation for.
     */
    protected int counter = 0;
    /**
     * Configuration to iterate over
     */
    protected Configuration configuration;
    /**
     * JobShop problem
     */
    protected JobShop problem;

    /**
     * Creates new iterator over a configuration (and given problem).
     *
     * @param configuration configuration to create operations for
     * @param problem       problem that configuration is part of
     */
    public JobShopIterator(Configuration configuration, JobShop problem) {
        this.configuration = configuration;
        this.problem = problem;
        this.counter = 0;
    }

    /**
     * Returns operation for given counter.
     *
     * @param counter counter to return operation for
     * @return operation for this counter
     */
    protected MoveOperation counterToOperation(int counter) {
        int jobIndex = counter / (this.problem.machines - 1);
        int machineIndex = counter % (this.problem.machines - 1);
        if (machineIndex >= this.configuration.valueAt(jobIndex))
            machineIndex++;
        return this.problem.moveOperations.get(jobIndex).get(machineIndex);
    }

    public MoveOperation getRandomOperation() throws UnsupportedOperationException {
        return counterToOperation(JcopRandom.nextInt(this.problem.getDimension() * (this.problem.machines - 1)));
    }

    public boolean hasNext() {
        return this.counter < (this.problem.getDimension() * (this.problem.machines - 1));
    }

    public MoveOperation next() {
        if (!this.hasNext())
            throw new NoSuchElementException("JobShop iterator has no more operations");
        return this.counterToOperation(this.counter++);
    }

    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("SAT does not support remove()");
    }
}
