/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Objective problem groups all problem interfaces into one and adds methods to determine if an implementation has these
 * interfaces really implemented or just they throw an exception if interface's method is called.
 *
 * @author Ondrej Skalicka
 * @see BaseObjectiveProblem BaseObjectiveProblem is basic implementation of ObjectiveProblem interface and should be
 *      base class for all problems (or at least all local search problems).
 */
public interface ObjectiveProblem extends StartingConfigurationProblem, RandomConfigurationProblem, DestinationProblem, Problem, GlobalSearchProblem {

    /**
     * Checks if problem has destination.
     *
     * @return true if implements DestinationProblem interface
     */
    public boolean hasDestination();

    /**
     * Checks if problem has starting attributes.
     *
     * @return true if implements StartingConfigurationProblem interface
     */
    public boolean hasStartingConfiguration();

    /**
     * Checks if problem has random attributes(s).
     *
     * @return true if implements RandomConfigurationProblem interface
     */
    public boolean hasRandomConfiguration();

    /**
     * Checks if problem has what is required to do global search on it.
     *
     * @return true if implements GlobalSearchProblem interface
     */
    public boolean hasGlobalSearch();

    /**
     * Returns problem around which ObjectiveProblem is wrapped.
     *
     * @return wrapped problem
     */
    public Problem getProblem ();
}