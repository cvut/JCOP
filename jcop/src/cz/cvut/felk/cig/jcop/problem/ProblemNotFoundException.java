/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Thrown when problem is not found, for example loading from empty file.
 *
 * @author Ondrej Skalicka
 */
public class ProblemNotFoundException extends RuntimeException {
    /**
     * Constructs an ProblemNotFoundException with the specified detail message.
     *
     * @param message the String that contains a detailed message
     */
    public ProblemNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs an ProblemNotFoundException with no detail message.
     */
    public ProblemNotFoundException() {
    }
}
