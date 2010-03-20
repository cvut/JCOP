/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.problem;

import cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException;

import java.util.List;

/**
 * This class should be base for all problems (at least local search problems) implemented.
 *
 * @author Ondrej Skalicka
 */
public final class BaseObjectiveProblem implements ObjectiveProblem {
    /**
     * Given problem.
     * <p/>
     * All {@link cz.cvut.felk.cig.jcop.problem.Problem} methods' calls are redirected to this object.
     */
    protected Problem problem;
    /**
     * Problem casted to {@link cz.cvut.felk.cig.jcop.problem.DestinationProblem}, if problem implemented such
     * interface.
     * <p/>
     * All DestinationProblem methods' calls are redirected to this object, if it is not null, or throws an {@link
     * cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException} otherwise.
     */
    protected DestinationProblem destinationProblem = null;
    /**
     * Problem casted to {@link cz.cvut.felk.cig.jcop.problem.StartingConfigurationProblem}, if problem implemented such
     * interface.
     * <p/>
     * All DestinationProblem methods' calls are redirected to this object, if it is not null, or throws an {@link
     * cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException} otherwise.
     */
    protected StartingConfigurationProblem startingConfigurationProblem = null;
    /**
     * Problem casted to {@link cz.cvut.felk.cig.jcop.problem.RandomConfigurationProblem}, if problem implemented such
     * interface.
     * <p/>
     * All RandomConfigurationProblem methods' calls are redirected to this object, if it is not null, or throws an
     * {@link cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException} otherwise.
     */
    protected RandomConfigurationProblem randomConfigurationProblem = null;
    /**
     * Problem casted to {@link cz.cvut.felk.cig.jcop.problem.GlobalSearchProblem}, if problem implemented such
     * interface.
     * <p/>
     * All GlobalSearchProblem methods' calls are redirected to this object, if it is not null, or throws an {@link
     * cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException} otherwise.
     */
    protected GlobalSearchProblem globalSearchProblem = null;
    /**
     * Checks whether problem implemented {@link cz.cvut.felk.cig.jcop.problem.DestinationProblem} interface.
     */
    protected boolean hasDestination = false;
    /**
     * Checks whether problem implemented {@link cz.cvut.felk.cig.jcop.problem.StartingConfigurationProblem} interface.
     */
    protected boolean hasStartingConfiguration = false;
    /**
     * Checks whether problem implemented {@link cz.cvut.felk.cig.jcop.problem.RandomConfigurationProblem} interface.
     */
    protected boolean hasRandomConfiguration = false;
    /**
     * Checks whether problem implemented {@link cz.cvut.felk.cig.jcop.problem.GlobalSearchProblem} interface.
     */
    protected boolean hasGlobalSearch = false;

    /**
     * Creates new BaseObjectiveProblem which wraps around supplied problem.
     * <p/>
     * BaseObjectiveProblem emulates all problem interfaces by redirecting method calls to problem (if it implements
     * such interface) or throws an exception (if it does not).
     *
     * @param problem problem to have BaseObjectiveProblem wrapped around
     */
    public BaseObjectiveProblem(Problem problem) {
        this.problem = problem;

        if (problem instanceof DestinationProblem) {
            this.destinationProblem = (DestinationProblem) problem;
            this.hasDestination = true;
        }

        if (problem instanceof StartingConfigurationProblem) {
            this.startingConfigurationProblem = (StartingConfigurationProblem) problem;
            this.hasStartingConfiguration = true;
        }

        if (problem instanceof RandomConfigurationProblem) {
            this.randomConfigurationProblem = (RandomConfigurationProblem) problem;
            this.hasRandomConfiguration = true;
        }

        if (problem instanceof GlobalSearchProblem) {
            this.globalSearchProblem = (GlobalSearchProblem) problem;
            this.hasGlobalSearch = true;
        }
    }

    @Override
    public String toString() {
        return problem.toString();
    }

    public Problem getProblem() {
        return this.problem;
    }

    /* Problem interface */

    public boolean isSolution(Configuration configuration) {
        return this.problem.isSolution(configuration);
    }

    public int getDimension() {
        return this.problem.getDimension();
    }

    public Fitness getDefaultFitness() {
        return this.problem.getDefaultFitness();
    }

    public OperationIterator getOperationIterator(Configuration configuration) {
        return this.problem.getOperationIterator(configuration);
    }

    public ConfigurationMap getConfigurationMap() {
        return this.problem.getConfigurationMap();
    }

    public String getLabel() {
        return this.problem.getLabel();
    }

    public void setLabel(String label) {
        this.problem.setLabel(label);
    }

    /* StartingConfigurationProblem interface */

    public Configuration getStartingConfiguration() {
        if (this.hasStartingConfiguration)
            return this.startingConfigurationProblem.getStartingConfiguration();

        throw new InvalidProblemException("This problem is not StartingConfigurationProblem");
    }

    public boolean hasStartingConfiguration() {
        return this.hasStartingConfiguration;
    }

    /* RandomConfigurationProblem interface */

    public Configuration getRandomConfiguration() {
        if (this.hasRandomConfiguration)
            return this.randomConfigurationProblem.getRandomConfiguration();

        throw new InvalidProblemException("This problem is not RandomConfigurationProblem");
    }

    public boolean hasRandomConfiguration() {
        return this.hasRandomConfiguration;
    }

    /* DestinationProblem interface */

    public List<Configuration> getDestinations() {
        if (this.hasDestination)
            return this.destinationProblem.getDestinations();

        throw new InvalidProblemException("This problem is not DestinationProblem");
    }

    public boolean hasDestination() {
        return this.hasDestination;
    }

    /* GlobalSearchProblem interface */

    public Integer getMaximum(int index) {
        if (this.hasGlobalSearch)
            return this.globalSearchProblem.getMaximum(index);

        throw new InvalidProblemException("This problem is not GlobalSearchProblem");
    }

    public boolean hasGlobalSearch() {
        return this.hasGlobalSearch;
    }
}