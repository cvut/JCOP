/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.condition;

import cz.cvut.felk.cig.jcop.solver.message.Message;
import cz.cvut.felk.cig.jcop.solver.message.MessageSolutionFound;
import cz.cvut.felk.cig.jcop.solver.message.MessageSolverStart;

/**
 * Evaluates to true when a solution is found (usually good for destination problems).
 *
 * @author Ondrej Skalicka
 */
public class FoundSolutionCondition extends BaseCondition {
    /**
     * If solution was yet found.
     */
    protected boolean solutionFound = false;

    public boolean isConditionMet() {
        return solutionFound;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof MessageSolutionFound) this.solutionFound = true;
        else if (message instanceof MessageSolverStart) this.solutionFound = false;
    }
}
