/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.condition;

import cz.cvut.felk.cig.jcop.solver.message.Message;
import cz.cvut.felk.cig.jcop.solver.message.MessageOptimize;
import cz.cvut.felk.cig.jcop.solver.message.MessageSolverStart;

/**
 * Limits solver to run only certain amount of iterations.
 *
 * @author Ondrej Skalicka
 */
public class IterationCondition extends BaseCondition {
    /**
     * Current number of iterations from message {@link cz.cvut.felk.cig.jcop.solver.message.MessageOptimize}.
     */
    protected int iterations;
    /**
     * Threshold for iterations - if {@link #iterations} reaches this number, this condition is considered met.
     */
    protected int iterationThreshold;

    /**
     * Creates new IterationCondition with given threshold.
     *
     * @param iterationThreshold how many iterations are allowed
     */
    public IterationCondition(int iterationThreshold) {
        this.iterationThreshold = iterationThreshold;
        this.iterations = 0;
    }

    public boolean isConditionMet() {
        return this.iterations >= this.iterationThreshold;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof MessageOptimize) this.iterations++;
        else if (message instanceof MessageSolverStart) this.iterations = 0;
    }
}
