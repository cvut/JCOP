/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Problem implementing StartingConfigurationProblem is one where there is always possible to find one starting
 * attributes.
 * <p/>
 * For example knapsack problem has starting attributes empty knapsack.
 *
 * @author Ondrej Skalicka
 * @see RandomConfigurationProblem RandomConfigurationProblem for problem which could have several starting
 *      configurations randomly generated.
 */
public interface StartingConfigurationProblem {

    /**
     * Gets starting attributes for a problem.
     *
     * @return starting attributes for a problem.
     */
    public abstract Configuration getStartingConfiguration();

}