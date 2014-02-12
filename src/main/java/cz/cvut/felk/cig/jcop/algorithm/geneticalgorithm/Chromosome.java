/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import cz.cvut.felk.cig.jcop.problem.Configuration;

/**
 * Chromosome is wrapped around configuration and hold provides information/functionality.
 * <p/>
 * Chromosome keeps fitness of its configuration as well as it allows being sorted {@link Comparable}.
 *
 * @author Ondrej Skalicka
 */
public class Chromosome implements Comparable<Chromosome> {
    /**
     * Configuration of chromosome.
     */
    protected Configuration configuration;
    /**
     * Fitness of chromosome (if set).
     */
    protected double fitness;
    /**
     * If true, fitness of chromosome is set. If false, fitness is undefined.
     */
    protected boolean hasFitness;

    /**
     * Creates new chromosome with fitness set.
     *
     * @param configuration configuration of chromosome
     * @param fitness       fitness of configuration
     */
    public Chromosome(Configuration configuration, double fitness) {
        this.configuration = configuration;
        this.fitness = fitness;
        this.hasFitness = true;
    }

    /**
     * Creates new chromosome with undefined fitness (to be set later with {@link #setFitness(double)}.
     *
     * @param configuration configuration of chromosome
     */
    public Chromosome(Configuration configuration) {
        this.configuration = configuration;
        this.hasFitness = false;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Checks if chromosome has fitness set.
     *
     * @return true if fitness is set, false otherwise
     */
    public boolean hasFitness() {
        return hasFitness;
    }

    /**
     * Returns fitness of chromosome.
     *
     * @return fitness of chromosome
     * @throws IllegalStateException if fitness is no set
     */
    public double getFitness() throws IllegalStateException {
        if (!this.hasFitness)
            throw new IllegalStateException("Cannot access fitness of chromosome which does not have it set");
        return fitness;
    }

    /**
     * Sets fitness of chromosome.
     *
     * @param fitness fitness of chromosome
     */
    public void setFitness(double fitness) {
        this.hasFitness = true;
        this.fitness = fitness;
    }

    public int compareTo(Chromosome o) {
        double thisVal = this.getFitness();
        double otherVal = o.getFitness();

        return (thisVal < otherVal ? 1 : (thisVal == otherVal ? 0 : -1));
    }
}
