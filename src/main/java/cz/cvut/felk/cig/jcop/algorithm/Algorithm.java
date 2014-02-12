/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm;

import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.Fitness;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;

/**
 * Common interface for all algorithms.
 * <p/>
 * Defines basic operations all algorithms must implement in order to be able to be used in JCOP.
 *
 * @author Ondrej Skalicka
 * @see BaseAlgorithm Base abstract implementation, reducing work required to add new algorithm
 */
public interface Algorithm {
    /**
     * Initializes new Algorithm on a problem.
     * <p/>
     * Algorithm is expected to prepare its fitness at this point, since solver can override it afterwards.
     * <p/>
     * Also, keep in mind that one algorithm could be used repeatedly on several different problems, so init should
     * reset all local settings.
     *
     * @param problem problem to be initialized on
     * @throws InvalidProblemException if supplied problem cannot be solved by this algorithm
     */
    public void init(ObjectiveProblem problem) throws InvalidProblemException;

    /**
     * Performs one optimization step.
     *
     * @throws CannotContinueException if no more optimization steps are possible
     */
    public void optimize() throws CannotContinueException;

    /**
     * Returns best found configuration.
     *
     * @return best found configuration
     */
    public Configuration getBestConfiguration();

    /**
     * Performs clean up after algorithm stopped.
     */
    public void cleanUp ();

    /**
     * Updates which fitness to use instead of default. Should be called before first optimize step is made.
     *
     * @param fitness fitness to be used in algorithm
     */
    public void setFitness(Fitness fitness);

    /**
     * Returns fitness of best found configuration.
     *
     * @return fitness of best found configuration
     */
    public double getBestFitness();

    /**
     * Sets label for algorithm.
     * <p/>
     * Note that label is important because {@link cz.cvut.felk.cig.jcop.result.ResultEntry} uses it to distinguish
     * between different settings of same algorithm.
     * <p/>
     * This should be called automatically in constructor (and no later than in {@link
     * #init(cz.cvut.felk.cig.jcop.problem.ObjectiveProblem)}, because {@link cz.cvut.felk.cig.jcop.solver.Solver} might
     * need to override it.
     *
     * @param label new label for algorithm
     */
    public void setLabel(String label);

    /**
     * Gets label of algorithm.
     * <p/>
     * For more info, see {@link #setLabel(String)}.
     *
     * @return label for algorithm
     */
    public String getLabel();
}