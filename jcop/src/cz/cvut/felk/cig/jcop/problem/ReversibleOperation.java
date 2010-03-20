/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * This interface provides means for operation that have reverse - eg. it is possible to negate effect of one operation
 * using its reverse.
 *
 * @author Ondrej Skalicka
 */
public interface ReversibleOperation extends Operation {
    /**
     * Returns reverse for this operation
     *
     * @return reverse for this operation
     */
    public ReversibleOperation getReverse();

    /**
     * Sets reverse for this reversible operation.
     *
     * @param reverse reverse for this operation
     */
    public void setReverse(ReversibleOperation reverse);
}