/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Thrown for invalid format to initialize problem from, for example file has invalid number of columns.
 *
 * @author Ondrej Skalicka
 */
public class ProblemFormatException extends RuntimeException {
    /**
     * Constructs an ProblemFormatException with the specified detail message.
     *
     * @param message the String that contains a detailed message
     */
    public ProblemFormatException(String message) {
        super(message);
    }

    /**
     * Constructs an ProblemFormatException with no detail message.
     */
    public ProblemFormatException() {
    }
}