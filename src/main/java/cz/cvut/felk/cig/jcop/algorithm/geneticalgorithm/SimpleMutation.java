/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.geneticalgorithm;

import cz.cvut.felk.cig.jcop.problem.GlobalSearchProblem;
import cz.cvut.felk.cig.jcop.util.JcopRandom;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Simple mutation, mutates up to one configuration variable.
 *
 * @author Ondrej Skalicka
 */
public class SimpleMutation implements Mutation {
    /**
     * Chance that mutation will take place.
     * <p/>
     * 0.0 means no chance, 1.0 means always.
     */
    protected double mutationRate;
    /**
     * Reference back to problem to be able to get minimal a maximal values for a configuration.
     */
    protected GlobalSearchProblem problem;

    /**
     * Creates new simple mutation for with given problem and mutation rate.
     *
     * @param problem      reference back to problem to be able to get minimal a maximal values for a configuration
     * @param mutationRate chance that mutation will take place
     */
    public SimpleMutation(GlobalSearchProblem problem, double mutationRate) {
        if (mutationRate < 0.0 || mutationRate > 1.0)
            throw new InvalidParameterException("MutationRate be lowe than 0.0 or grater than 1.0");

        this.mutationRate = mutationRate;
        this.problem = problem;
    }

    public void mutate(List<Integer> attributes) {
        if (JcopRandom.nextDouble() < this.mutationRate) {
            int index = JcopRandom.nextInt(attributes.size());
            int max = this.problem.getMaximum(index);
            int value = JcopRandom.nextInt(max);
            attributes.set(index, value);
        }
    }
}
