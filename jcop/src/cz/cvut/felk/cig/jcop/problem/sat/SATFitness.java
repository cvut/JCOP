/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem.sat;

import cz.cvut.felk.cig.jcop.problem.BaseFitness;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Fitness;

/**
 * Default fitness of a SAT problem.
 * <p/>
 * If satisfied, result is maximal possible weight - current weight (eg. configurations with lower weight has higher
 * fitness).
 * <p/>
 * If not satisfied, result is negative number of non-satisfied clauses.
 *
 * @author Ondrej Skalicka
 */
public class SATFitness extends BaseFitness implements Fitness {
    /**
     * Reference to {@link SAT} problem.
     */
    protected SAT problem;


    /**
     * Creates new TSPFitness with reference to {@link SAT} problem.
     *
     * @param problem SAT problem
     */
    public SATFitness(SAT problem) {
        this.problem = problem;
        /* BaseFitness */
        this.minFitness = -this.problem.formula.clauses.size();
        this.maxFitness = 0;
        for (Variable variable : this.problem.variables) {
            this.maxFitness += variable.getWeight();
        }
    }

    public double getValue(Configuration configuration) {
        int remainingClauses = 0;
        for (Clause clause : this.problem.formula.clauses) {
            if (!clause.isTrue(configuration)) remainingClauses++;
        }
        if (remainingClauses > 0) return -remainingClauses;

        int remainingWeight = 0;
        for (Variable variable : this.problem.variables) {
            if (configuration.valueAt(variable.getIndex()) == 0)
                remainingWeight += variable.getWeight();
        }

        return remainingWeight;
    }
}
