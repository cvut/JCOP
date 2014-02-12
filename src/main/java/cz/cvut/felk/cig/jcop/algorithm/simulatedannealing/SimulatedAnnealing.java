/*
 * Copyright © 2010 by Ondrej Skalicka. All Rights Reserved
 */

package cz.cvut.felk.cig.jcop.algorithm.simulatedannealing;

import cz.cvut.felk.cig.jcop.algorithm.BaseAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.CannotContinueException;
import cz.cvut.felk.cig.jcop.algorithm.ChainAlgorithm;
import cz.cvut.felk.cig.jcop.algorithm.InvalidProblemException;
import cz.cvut.felk.cig.jcop.problem.Configuration;
import cz.cvut.felk.cig.jcop.problem.ObjectiveProblem;
import cz.cvut.felk.cig.jcop.problem.Operation;
import cz.cvut.felk.cig.jcop.util.JcopRandom;
import org.apache.log4j.Logger;

/**
 * Simulated annealing algorithm.
 * <p/>
 * This algorithm is created with starting temperature and anneal coefficient. In every step of {@link
 * #init(cz.cvut.felk.cig.jcop.problem.ObjectiveProblem)}, algorithm performs one randomly selected operation on active
 * configuration. New configuration is accepted as active iff new configuration is better than current active or it
 * passes
 * <p/>
 * e<sup>-&Delta;D/T</sup> > Random(0.0, 0.1)
 * <p/>
 * equation, where &Delta;D is difference of normalized fitnesses of active configuration and new configuration, and T
 * is temperature.
 * <p/>
 * Every step temperature is lowered by anneal coefficient, eg. temperature = temperature * anneal.
 *
 * @author Ondrej Skalicka
 */
public class SimulatedAnnealing extends BaseAlgorithm implements ChainAlgorithm {
    /**
     * Active configuration to be expanded
     */
    protected Configuration activeConfiguration;
    /**
     * Fitness of active configuration
     */
    protected double activeFitness;
    /**
     * Normalized fitness of active configuration
     */
    protected double activeNormalizedFitness;
    /**
     * Current temperature
     */
    protected double temperature;
    /**
     * Annealing coefficient
     */
    protected double anneal;
    /**
     * Backup of {@link #temperature} in case algorithm will be used repeatedly
     */
    protected double startingTemperature;

    /**
     * Creates new annealing algorithm instance with given anneal coefficient and temperature.
     *
     * @param anneal      annealing coefficient
     * @param temperature starting temperature
     */
    public SimulatedAnnealing(double temperature, double anneal) {
        this.startingTemperature = temperature;
        this.anneal = anneal;
    }

    /**
     * Creates new annealing with default anneal coefficient (0.999) and starting temperature (10.0).
     */
    public SimulatedAnnealing() {
        this(10, 0.999);
    }

    public void init(ObjectiveProblem problem) throws InvalidProblemException {
        this.problem = problem;

        // SA requires either startingConfiguration or RandomStartingConfiguration problem
        if (!problem.hasStartingConfiguration() && !problem.hasRandomConfiguration())
            throw new InvalidProblemException("SimulatedAnnealing algorithm requires either StartingConfigurationProblem or RandomConfigurationProblem");

        // fetch starting configuration
        if (problem.hasStartingConfiguration()) {
            this.activeConfiguration = problem.getStartingConfiguration();
        } else {
            this.activeConfiguration = problem.getRandomConfiguration();
        }

        this.initCommon();
    }

    public void init(ObjectiveProblem problem, Configuration activeConfiguration) {
        this.problem = problem;
        
        this.activeConfiguration = activeConfiguration;

        this.initCommon();
    }

    /**
     * Part of init common to all initialization types.
     */
    protected void initCommon () {
        this.fitness = problem.getDefaultFitness();

        this.temperature = this.startingTemperature;
        this.setLabel("T=" + this.temperature + ", A=" + this.anneal);

        this.bestConfiguration = this.activeConfiguration;
        this.bestFitness = this.fitness.getValue(this.activeConfiguration);

        // calculate fitness
        this.activeFitness = this.fitness.getValue(this.activeConfiguration);
        this.activeNormalizedFitness = this.fitness.normalize(activeFitness);
    }

    public void optimize() throws CannotContinueException {
        // fetch random operation
        Operation operation = this.problem.getOperationIterator(this.activeConfiguration).getRandomOperation();
        if (operation == null)
            throw new CannotContinueException("Unable to get random operation");

        // expand to new configuration
        Configuration newConfiguration = operation.execute(this.activeConfiguration);

        // calculate fitness
        double newFitness = this.fitness.getValue(newConfiguration);
        double newNormalizedFitness = this.fitness.normalize(newFitness);

        // new configuration is better or passes temperature test
        if (newFitness > this.activeNormalizedFitness || JcopRandom.nextDouble() < Math.exp((newNormalizedFitness - this.activeNormalizedFitness) / this.temperature)) {
            // set new configuration as active
            this.activeConfiguration = newConfiguration;
            this.activeFitness = newFitness;
            this.activeNormalizedFitness = newNormalizedFitness;
            // if it is best, set it as best
            if (newFitness > this.bestFitness) {
                Logger.getLogger(this.getClass()).debug("Better solution " + newFitness + ", " + newConfiguration);
                this.bestConfiguration = newConfiguration;
                this.bestFitness = newFitness;
            }
        }

        // anneal
        this.temperature *= this.anneal;
    }

}
