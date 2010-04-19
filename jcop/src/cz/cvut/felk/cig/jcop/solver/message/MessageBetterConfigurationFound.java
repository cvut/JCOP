/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.message;

import cz.cvut.felk.cig.jcop.problem.Configuration;

/**
 * Notifies that better configuration was found (than current best one).
 *
 * @author Ondrej Skalicka
 */
public class MessageBetterConfigurationFound implements Message {
    /**
     * New best found solution.
     */
    protected Configuration configuration;
    /**
     * Fitness of best solution
     */
    protected double fitness;

    /**
     * Creates new message that new best solution was found.
     *
     * @param configuration new best configuration
     * @param fitness       fitness of best solution
     */
    public MessageBetterConfigurationFound(Configuration configuration, double fitness) {
        this.configuration = configuration;
        this.fitness = fitness;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public double getFitness() {
        return fitness;
    }
}
