/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Thrown when operation is used on configuration which it is unable to modify.
 *
 * @author Ondrej Skalicka
 */
public class InvalidConfigurationException extends RuntimeException {
    /**
     * Constructs an InvalidConfigurationException with the specified detail message.
     *
     * @param message the String that contains a detailed message
     */
    public InvalidConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs an InvalidConfigurationException with no detail message.
     */
    public InvalidConfigurationException() {
    }
}
