/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm;

/**
 * This exception is raised when algorithm is run on problem it cannot solve.
 * <p/>
 * Example: algorithm requires starting attributes, but problem does not supply it.
 */
public class InvalidProblemException extends UnsupportedOperationException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an InvalidProblemException with the specified detail message.
     *
     * @param message the String that contains a detailed message
     */
    public InvalidProblemException(String message) {
        super(message);
    }

    /**
     * Constructs an InvalidProblemException with no detail message.
     */
    public InvalidProblemException() {
    }
}
