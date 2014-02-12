/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.result;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Problem;
import cz.cvut.felk.cig.jcop.util.PreciseTimestamp;

/**
 * Contains information about single run of algorithm on a problem.
 * <p/>
 * Apart from algorithm and problem used, stores number of optimizations, best solution and start/stop timestamp.
 * <p/>
 * There are 4 different constructors for every combination of missing/present stopTimestamp and exception.
 *
 * @author Ondrej Skalicka
 */
public class ResultEntry {
    /**
     * Algorithm for this entry.
     */
    protected Algorithm algorithm;
    /**
     * Problem for this entry.
     */
    protected Problem problem;
    /**
     * Number of {@link cz.cvut.felk.cig.jcop.algorithm.Algorithm#optimize()} calls.
     */
    protected int optimizeCounter;
    /**
     * Best found solution.
     */
    protected Configuration bestConfiguration;
    /**
     * Starting timestamp.
     */
    protected PreciseTimestamp startTimestamp;
    /**
     * Ending timestamp.
     */
    protected PreciseTimestamp stopTimestamp;
    /**
     * Exception which forced algorithm to stop.
     */
    protected Exception exception;
    /**
     * Fitness of bestConfiguration
     */
    protected double bestFitness;

    /**
     * Creates new entry with all attributes given.
     *
     * @param algorithm         algorithm used
     * @param problem           problem solved
     * @param bestConfiguration best found configuration
     * @param bestFitness       fitness of best found configuration
     * @param optimizeCounter   number of {@link cz.cvut.felk.cig.jcop.algorithm.Algorithm#optimize()} calls
     * @param exception         exception which caused algorithm to stop
     * @param startTimestamp    starting time
     * @param stopTimestamp     ending time
     */
    public ResultEntry(Algorithm algorithm, Problem problem, Configuration bestConfiguration, double bestFitness, int optimizeCounter, Exception exception, PreciseTimestamp startTimestamp, PreciseTimestamp stopTimestamp) {
        this.exception = exception;
        this.algorithm = algorithm;
        this.problem = problem;
        this.bestConfiguration = bestConfiguration;
        this.bestFitness = bestFitness;
        this.optimizeCounter = optimizeCounter;
        this.startTimestamp = startTimestamp;
        this.stopTimestamp = stopTimestamp;
    }

    /**
     * Creates new entry with stopTimestamp set to current {@link PreciseTimestamp}.
     *
     * @param algorithm         algorithm used
     * @param problem           problem solved
     * @param bestConfiguration best found configuration
     * @param bestFitness       fitness of best found configuration
     * @param optimizeCounter   number of {@link cz.cvut.felk.cig.jcop.algorithm.Algorithm#optimize()} calls
     * @param exception         exception which caused algorithm to stop
     * @param startTimestamp    starting time
     */
    public ResultEntry(Algorithm algorithm, Problem problem, Configuration bestConfiguration, double bestFitness, int optimizeCounter, Exception exception, PreciseTimestamp startTimestamp) {
        this(algorithm, problem, bestConfiguration, bestFitness, optimizeCounter, exception, startTimestamp, new PreciseTimestamp());
    }

    /**
     * Creates new entry with null exception.
     *
     * @param algorithm         algorithm used
     * @param problem           problem solved
     * @param bestConfiguration best found configuration
     * @param bestFitness       fitness of best found configuration
     * @param optimizeCounter   number of {@link cz.cvut.felk.cig.jcop.algorithm.Algorithm#optimize()} calls
     * @param startTimestamp    starting time
     * @param stopTimestamp     ending time
     */
    public ResultEntry(Algorithm algorithm, Problem problem, Configuration bestConfiguration, double bestFitness, int optimizeCounter, PreciseTimestamp startTimestamp, PreciseTimestamp stopTimestamp) {
        this(algorithm, problem, bestConfiguration, bestFitness, optimizeCounter, null, startTimestamp, stopTimestamp);
    }

    /**
     * Creates new entry with stopTimestamp set to current {@link PreciseTimestamp} and with null exception.
     *
     * @param algorithm         algorithm used
     * @param problem           problem solved
     * @param bestConfiguration best found configuration
     * @param bestFitness       fitness of best found configuration
     * @param optimizeCounter   number of {@link cz.cvut.felk.cig.jcop.algorithm.Algorithm#optimize()} calls
     * @param startTimestamp    starting time
     */
    public ResultEntry(Algorithm algorithm, Problem problem, Configuration bestConfiguration, double bestFitness, int optimizeCounter, PreciseTimestamp startTimestamp) {
        this(algorithm, problem, bestConfiguration, bestFitness, optimizeCounter, null, startTimestamp, new PreciseTimestamp());
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Configuration getBestConfiguration() {
        return bestConfiguration;
    }

    public int getOptimizeCounter() {
        return optimizeCounter;
    }

    public Problem getProblem() {
        return problem;
    }

    public PreciseTimestamp getStartTimestamp() {
        return startTimestamp;
    }

    public PreciseTimestamp getStopTimestamp() {
        return stopTimestamp;
    }

    public Exception getException() {
        return exception;
    }

    public double getBestFitness() {
        return bestFitness;
    }
}
