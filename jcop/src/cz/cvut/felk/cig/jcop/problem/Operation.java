/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Operation is transformation of one {@link Configuration attributes} to another. Operations are part of definition of
 * every problem.
 *
 * @author Ondrej Skalicka
 */
public interface Operation {

    /**
     * Executes operation on a attributes.
     * <p/>
     * Creates whole new attributes, no references to the old one are kept (except references via {@link
     * OperationHistory}.
     *
     * @param configuration attributes to have operation executed upon
     * @return new attributes, after operation execution
     * @throws InvalidConfigurationException if operation is used on configuration which it is unable to modify
     */
    public Configuration execute(Configuration configuration) throws InvalidConfigurationException;
}