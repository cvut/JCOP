/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm;

/**
 * This exception is raised whenever algorithm is unable to continue.
 * <p/>
 * Algorithm can be unable to continue for example when there are no more items in queue in a {@link
 * cz.cvut.felk.cig.jcop.algorithm.graphsearch.GraphSearch Graph Search algorithm}.
 *
 * @author Ondrej Skalicka
 */
public class CannotContinueException extends IllegalStateException {
    /**
     * Constructs an CannotContinueException with the specified detail message.
     *
     * @param message the String that contains a detailed message
     */
    public CannotContinueException(String message) {
        super(message);
    }

    /**
     * Constructs an CannotContinueException with no detail message.
     */
    public CannotContinueException() {
    }
}