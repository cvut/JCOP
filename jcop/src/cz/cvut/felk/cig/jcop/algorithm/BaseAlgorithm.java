/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Fitness;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;

/**
 * Implements some methods of {@link Algorithm} identical for most algorithms.
 * <p/>
 * Reduces work required to add new algorithm by implementing methods which are identical for most algorithms.
 * <p/>
 * Note that to algorithms are considered equal if they are of same class (eg. getClass().equals(this.getClass())
 * equals) and their labels are equal.
 *
 * @author Ondrej Skalicka
 */
public abstract class BaseAlgorithm implements Algorithm {
    /**
     * Reference to problem which is being solved.
     */
    protected ObjectiveProblem problem;
    /**
     * Best found configuration
     */
    protected Configuration bestConfiguration;
    /**
     * Fitness of {@link #bestConfiguration best found configuration}
     */
    protected double bestFitness;
    /**
     * Fitness object to be used to calculate fitness of configurations.
     */
    protected Fitness fitness;
    /**
     * Label for algorithm.
     * <p/>
     * For more info, see {@link cz.cvut.felk.cig.jcop.algorithm.Algorithm#setLabel(String)}.
     */
    protected String label = "";

    public Configuration getBestConfiguration() {
        return this.bestConfiguration;
    }

    public void cleanUp() {
        
    }

    public double getBestFitness() {
        return bestFitness;
    }
    /**
     * @inheritDoc
     *
     * Note that when fitness is set and there is a {@link #bestConfiguration} set, it recalculates {@link #bestFitness} as well.
     */
    public void setFitness(Fitness fitness) {
        this.fitness = fitness;

        if (this.bestConfiguration != null) this.bestFitness = fitness.getValue(this.bestConfiguration);
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!o.getClass().equals(this.getClass())) return false;
        BaseAlgorithm that = (BaseAlgorithm) o;

        return !(this.getLabel() != null ? !label.equals(that.getLabel()) : that.getLabel() != null);
    }

    @Override
    public String toString() {
        if ("".equals(this.getLabel())) return this.getClass().getSimpleName();
        return this.getClass().getSimpleName() + " [" + this.getLabel() + "]";
    }
}