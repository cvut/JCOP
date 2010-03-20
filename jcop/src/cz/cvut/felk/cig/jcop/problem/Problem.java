/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

/**
 * Most basic problem interface - every problem must implement this.
 * <p/>
 * Supplies most basic problem operations, such as initializing, getting fitness etc.
 *
 * @author Ondrej Skalicka
 * @see BaseObjectiveProblem BaseObjectiveProblem is abstract implementation of this interface along with other
 *      supplementary interfaces. Consider using it instead.
 */
public interface Problem {
    /**
     * Tests if attributes is solution to this problem.
     *
     * @param configuration tested attributes
     * @return true if attributes is solution to this problem
     */
    public boolean isSolution(Configuration configuration);

    /**
     * Returns dimension of this problem.
     *
     * @return dimension of problem
     */
    public int getDimension();

    /**
     * Returns default fitness for this problem.
     * <p/>
     * Default fitness is object which is able to rank any attributes so that better attributes has higher rank.
     *
     * @return fitness object
     */
    public Fitness getDefaultFitness();

    /**
     * Returns iterator for operations which are allowed for supplied attributes.
     * <p/>
     * This iterator should be lazy initialized! If not, you are running into risk of performance problems (some
     * algorithms creates lots of configurations and asks operationIterator for all of them).
     *
     * @param configuration attributes to return operationIterator for
     * @return operatorIterator for this attributes
     */
    public OperationIterator getOperationIterator(Configuration configuration);

    /**
     * Returns attributes mapping for this problem.
     * <p/>
     * Configuration mapping maps Integers from attributes to other objects (Strings etc) 1:1.
     *
     * @return configuration map for this problem
     * @see cz.cvut.felk.cig.jcop.problem.IdentityConfigurationMap default identity implementation of map
     */
    public ConfigurationMap getConfigurationMap();

    /**
     * Sets label for problem.
     * <p/>
     * Note that label is important because {@link cz.cvut.felk.cig.jcop.result.ResultEntry} uses it to distinguish
     * between different settings of same problem.
     * <p/>
     * This should be called automatically in constructor, because {@link cz.cvut.felk.cig.jcop.solver.Solver} might
     * need to override it.
     *
     * @param label new label for problem
     */
    public void setLabel(String label);

    /**
     * Gets label of problem.
     * <p/>
     * For more info, see {@link #setLabel(String)}.
     *
     * @return label for problem
     */
    public String getLabel();
}