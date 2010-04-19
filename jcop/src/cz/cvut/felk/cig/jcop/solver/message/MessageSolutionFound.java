/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.message;

import cz.cvut.felk.cig.jcop.problem.Configuration;

/**
 * Notifies that new solution was found.
 * <p/>
 * Solution is considered {@link cz.cvut.felk.cig.jcop.problem.Configuration} which passes {@link
 * cz.cvut.felk.cig.jcop.problem.Problem#isSolution(cz.cvut.felk.cig.jcop.problem.Configuration)}.
 *
 * @author Ondrej Skalicka
 */
public class MessageSolutionFound implements Message {
    /**
     * New best found solution.
     */
    protected Configuration configuration;
    /**
     * Fitness of best solution
     */
    protected double fitness;

    /**
     * Creates new message that new solution was found.
     *
     * @param configuration new solution
     * @param fitness       fitness of new solution
     */
    public MessageSolutionFound(Configuration configuration, double fitness) {
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
