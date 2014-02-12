/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Base implementation of {@link ReversibleOperation} interface.
 *
 * @author Ondrej Skalicka
 */
public abstract class BaseReversibleOperation implements ReversibleOperation, Operation {
    /**
     * Reverse operation for this operation.
     */
    protected ReversibleOperation reverse;

    public ReversibleOperation getReverse() {
        return reverse;
    }

    public void setReverse(ReversibleOperation reverse) {
        this.reverse = reverse;
    }
}
