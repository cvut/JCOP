/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.solver.condition;

/**
 * Implements some methods of {@link StopCondition} identical for most stop conditions for easier use.
 *
 * @author Ondrej Skalicka
 */
public abstract class BaseCondition implements StopCondition {
    public void onMessage(Object message) {
    }
}
