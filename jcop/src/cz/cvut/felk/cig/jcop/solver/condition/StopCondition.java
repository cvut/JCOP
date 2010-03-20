/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.condition;

import cz.cvut.felk.cig.jcop.solver.message.MessageListener;

/**
 * Base for all stop conditions, defines method required for a stop condition to be used in JCOP.
 *
 * @author Ondrej Skalicka
 * @see BaseCondition base abstract implementation
 */
public interface StopCondition extends MessageListener {
    /**
     * Returns true if condition is met
     *
     * @return true if condition is met
     */
    public boolean isConditionMet();

}