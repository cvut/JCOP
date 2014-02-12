/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.message;

import cz.cvut.felk.cig.jcop.algorithm.Algorithm;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;

/**
 * Notifies that optimization has started or been restarted in solver.
 *
 * @author Ondrej Skalicka
 */
public class MessageSolverStop implements Message {
    /**
     * Problem that has has stopped being solved.
     */
    protected ObjectiveProblem objectiveProblem;
    /**
     * Algorithm that has stopped solving.
     */
    protected Algorithm algorithm;

    /**
     * Creates new MessageSolverStop with algorithm solving some problem.
     *
     * @param algorithm        algorithm that solved the problem
     * @param objectiveProblem problem thas was solved
     */
    public MessageSolverStop(Algorithm algorithm, ObjectiveProblem objectiveProblem) {
        this.algorithm = algorithm;
        this.objectiveProblem = objectiveProblem;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public ObjectiveProblem getObjectiveProblem() {
        return objectiveProblem;
    }
}
