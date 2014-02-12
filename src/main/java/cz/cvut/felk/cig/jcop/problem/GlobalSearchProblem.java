/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * This interface allows usage of global search algorithms such as genetic algorithm or ant colony.
 * <p/>
 * Global search algorithms do not use operations so note that their {@link cz.cvut.felk.cig.jcop.problem.Configuration
 * configurations} has only one element in {@link cz.cvut.felk.cig.jcop.problem.Configuration#getOperationHistory()
 * operation history}, which is {@link cz.cvut.felk.cig.jcop.problem.UnknownOperation}.
 * <p/>
 * Note that only maximum is monitored, minimum is expected to be 0 (due to fact that mapping allows to have any range
 * of variables it is always possible to set minimum as 0).
 *
 * @author Ondrej Skalicka
 */
public interface GlobalSearchProblem {
    /**
     * Returns maximal possible value for configuration variable at index.
     *
     * @param index index of variable which maximal value we seek
     * @return maximal value for given index
     */
    public Integer getMaximum(int index);
}
